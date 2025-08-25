package com.examly.springapp.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.examly.springapp.enums.ComplianceStatus;
import com.examly.springapp.enums.RegistrationStatus;
import com.examly.springapp.enums.VerificationStatus;
import com.examly.springapp.model.User;
import com.examly.springapp.model.vendor.Vendor;
import com.examly.springapp.model.vendor.VendorCompliance;
import com.examly.springapp.model.vendor.VendorDocument;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.repository.VendorComplianceRepository;
import com.examly.springapp.repository.VendorDocumentRepository;
import com.examly.springapp.repository.VendorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final Cloudinary cloudinary;
    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final VendorComplianceRepository complianceRepository;

    public boolean documentExists(String type) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Vendor vendor = vendorRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return (vendorDocumentRepository.findByVendorVendorIdAndDocumentType(vendor.getVendorId(),type).isEmpty() ? false : true );
    }   
    
    public String uploadVendorDocument(String type, MultipartFile file) throws IOException, NoSuchAlgorithmException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));;
        Vendor vendor = vendorRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Vendor not found"));
        
        VendorDocument doc = new VendorDocument();
        
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        doc.setVendor(vendor);
        doc.setDocumentType(type);
        doc.setFileUrl(uploadResult.get("secure_url").toString());
        doc.setFileHash(generateFileHash(file.getBytes()));
        // doc.setFileHash("TEMP_HASH"); 
        // implement logic for filehash later;

        vendorDocumentRepository.save(doc);

        return doc.getFileUrl();
    }

    public List<VendorDocument> getAllUploadedDocs(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Vendor vendor = vendorRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Vendor not found"));

        return vendorDocumentRepository.findAllByVendorVendorId(vendor.getVendorId());   
    }

    public String uploadVendorDocument(String type, MultipartFile file, boolean overwrite) throws IOException, NoSuchAlgorithmException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Vendor vendor = vendorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        // 1️. Check if document already exists
        VendorDocument existingDoc = vendorDocumentRepository
                .findByVendorVendorIdAndDocumentType(vendor.getVendorId(), type)
                .orElse(null);

        if (existingDoc != null && !overwrite) {
            // If not overwriting, return a message or throw custom exception
            throw new RuntimeException("Document already exists for this type. Please confirm overwrite.");
        }

        if (existingDoc != null && overwrite) {
            // Optional: remove old file from Cloudinary if you store public_id
            // cloudinary.uploader().destroy(existingDoc.getPublicId(), ObjectUtils.emptyMap());
            vendorDocumentRepository.delete(existingDoc);
        }

        // 2️. Upload new file
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        VendorDocument newDoc = new VendorDocument();
        newDoc.setVendor(vendor);
        newDoc.setDocumentType(type);
        newDoc.setFileUrl(uploadResult.get("secure_url").toString());
        newDoc.setFileHash(generateFileHash(file.getBytes()));
        newDoc.setUploadDate(java.time.LocalDateTime.now());
        newDoc.setVerificationStatus(VerificationStatus.PENDING);

        vendorDocumentRepository.save(newDoc);

        return newDoc.getFileUrl();
    }

    public void deleteVendorDocument(Long documentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Vendor vendor = vendorRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Vendor not found"));

        VendorDocument doc = vendorDocumentRepository.findById(documentId)
            .orElseThrow(() -> new RuntimeException("Document not found"));

        if (!doc.getVendor().getVendorId().equals(vendor.getVendorId())) {
            throw new RuntimeException("You are not authorized to delete this document");
        }

        // Optional: Remove from Cloudinary
        // cloudinary.uploader().destroy(doc.getPublicId(), ObjectUtils.emptyMap());

        vendorDocumentRepository.delete(doc);
    }
    
    public String generateFileHash(byte[] fileBytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(fileBytes);
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    public VendorDocument updateDocumentStatus(Long docId, VerificationStatus status, String remarks, LocalDate expiryDate) {
        VendorDocument doc = vendorDocumentRepository.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        doc.setVerificationStatus(status);
        doc.setRemarks(remarks);
        doc.setVerificationDate(LocalDateTime.now());

        Vendor vendor = doc.getVendor();

        if (status == VerificationStatus.VERIFIED) {
            switch (doc.getDocumentType()) {
                case "BUSINESS_LICENSE":
                    vendor.setHasBusinessLicense(true);
                    vendor.setBusinessLicenseExpiryDate(expiryDate);
                    break;
                case "LIABILITY_INSURANCE":
                    vendor.setHasLiabilityInsurance(true);
                    vendor.setInsuranceExpiryDate(expiryDate);
                    break;
                case "VEGAN_CERTIFICATION":
                    vendor.setIsVeganCertified(true);
                    vendor.setVeganCertificationExpiry(expiryDate);
                    break;
            }
        }

        checkAndApproveRegistration(vendor);
        vendorRepository.save(vendor);
        return vendorDocumentRepository.save(doc);
    }


    public VendorCompliance updateComplianceStatus(Long complianceId, ComplianceStatus status, String notes, LocalDate expiryDate) {
    VendorCompliance compliance = complianceRepository.findById(complianceId)
            .orElseThrow(() -> new RuntimeException("Compliance record not found"));

    compliance.setStatus(status);
    compliance.setNotes(notes);

    Vendor vendor = compliance.getVendor();

    if (status == ComplianceStatus.COMPLIANT) {
        switch (compliance.getComplianceType()) {
            case LOCAL_HEALTH_PERMIT:
                vendor.setLocalComplianceStatus(ComplianceStatus.COMPLIANT);
                break;
            case HACCP:
                vendor.setHaccpCertified(true);
                vendor.setHaccpExpiryDate(expiryDate);
                break;
            case FDA_REGISTRATION:
                vendor.setFdaRegistrationNumber("Verified");
                vendor.setFdaRegistrationExpiry(expiryDate);
                break;
        }
    }

    checkAndApproveRegistration(vendor);
    vendorRepository.save(vendor);
    return complianceRepository.save(compliance);
}


    private void checkAndApproveRegistration(Vendor vendor) {
    if (Boolean.TRUE.equals(vendor.getHasBusinessLicense())
        && Boolean.TRUE.equals(vendor.getHasLiabilityInsurance())
        && Boolean.TRUE.equals(vendor.getIsVeganCertified())
        && Boolean.TRUE.equals(vendor.getHaccpCertified())
        && vendor.getFdaRegistrationNumber() != null
        && vendor.getLocalComplianceStatus() == ComplianceStatus.COMPLIANT) {

        vendor.setRegistrationStatus(RegistrationStatus.APPROVED);
        vendor.setApprovalDate(LocalDate.now());
        vendor.setApprovedBy("Admin System");
    }
}

}
