package com.examly.springapp.service;

import com.examly.springapp.dto.OrderItemDTO;
import com.examly.springapp.dto.OrderResponseDTO;
import com.examly.springapp.dto.VendorComplianceDTO;
import com.examly.springapp.dto.VendorRegistrationDTO;
import com.examly.springapp.dto.VendorResponseDTO;
import com.examly.springapp.enums.ComplianceStatus;
import com.examly.springapp.enums.ComplianceType;
import com.examly.springapp.enums.RegistrationStatus;
import com.examly.springapp.enums.VerificationStatus;
import com.examly.springapp.model.Orders;
import com.examly.springapp.model.User;
import com.examly.springapp.model.vendor.Vendor;
import com.examly.springapp.model.vendor.VendorCompliance;
import com.examly.springapp.model.vendor.VendorDocument;
import com.examly.springapp.repository.OrderRepository;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.repository.VendorComplianceRepository;
import com.examly.springapp.repository.VendorRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final VendorComplianceRepository complianceRepo;
    private final OrderRepository orderRepository;

    public VendorResponseDTO registerVendor(User user, VendorRegistrationDTO vendorDTO){    
        Vendor vendor = new Vendor();
        vendor.setUser(user);
        vendor.setCompanyName(vendorDTO.getCompanyName());
        vendor.setBusinessEmail(vendorDTO.getBusinessEmail());
        vendor.setPrimaryContactNumber(vendorDTO.getPrimaryContactNumber());
        vendor.setBusinessDescription(vendorDTO.getBusinessDescription());
        vendor.setBusinessRegistrationNumber(vendorDTO.getBusinessRegistrationNumber());
        vendor.setBusinessLicenseNumber(vendorDTO.getBusinessLicenseNumber());
        vendor.setTaxId(vendorDTO.getTaxId());
        vendor.setEstablishedYear(vendorDTO.getEstablishedYear());
        vendor.setWebsiteUrl(vendorDTO.getWebsiteUrl());
        vendor.setStreetAddress1(vendorDTO.getStreetAddress1());
        vendor.setStreetAddress2(vendorDTO.getStreetAddress2());
        vendor.setCity(vendorDTO.getCity());
        vendor.setState(vendorDTO.getState());
        vendor.setPostalCode(vendorDTO.getPostalCode());
        vendor.setRegistrationStatus(RegistrationStatus.PENDING);
        vendorRepository.save(vendor);

        VendorResponseDTO responseDTO = modelMapper.map(vendor, VendorResponseDTO.class); // to map entity to DTO
        return responseDTO;
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }


    public VendorResponseDTO getVendorProfile(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vendor vendor = vendorRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Vendor not found"));
        
        VendorResponseDTO responseDTO = modelMapper.map(vendor, VendorResponseDTO.class); // to map entity to DTO
        return responseDTO;
    }

    public VendorResponseDTO updateVendorProfile(VendorRegistrationDTO vendorDTO){

        // String email = (User)SecurityContextHolder.getContext().getAuthentication().getEmail();
        // User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));;
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vendor vendor = vendorRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.setCompanyName(vendorDTO.getCompanyName());
        vendor.setBusinessEmail(vendorDTO.getBusinessEmail());
        vendor.setPrimaryContactNumber(vendorDTO.getPrimaryContactNumber());
        vendor.setBusinessDescription(vendorDTO.getBusinessDescription());
        vendor.setBusinessRegistrationNumber(vendorDTO.getBusinessRegistrationNumber());
        vendor.setBusinessLicenseNumber(vendorDTO.getBusinessLicenseNumber());
        vendor.setTaxId(vendorDTO.getTaxId());
        vendor.setEstablishedYear(vendorDTO.getEstablishedYear());
        vendor.setWebsiteUrl(vendorDTO.getWebsiteUrl());
        vendor.setStreetAddress1(vendorDTO.getStreetAddress1());
        vendor.setStreetAddress2(vendorDTO.getStreetAddress2());
        vendor.setCity(vendorDTO.getCity());
        vendor.setState(vendorDTO.getState());
        vendor.setPostalCode(vendorDTO.getPostalCode());
        
        vendorRepository.save(vendor);
        return mapToResponseDTO(vendor);
    }

    public VendorResponseDTO mapToResponseDTO(Vendor vendor){ 
        VendorResponseDTO vendorResponse = new VendorResponseDTO();
        vendorResponse.setVendorId(vendor.getVendorId());
        vendorResponse.setCompanyName(vendor.getCompanyName());
        vendorResponse.setBusinessEmail(vendor.getBusinessEmail());
        vendorResponse.setPrimaryContactNumber(vendor.getPrimaryContactNumber());
        vendorResponse.setBusinessDescription(vendor.getBusinessDescription());
        vendorResponse.setBusinessRegistrationNumber(vendor.getBusinessRegistrationNumber());
        vendorResponse.setTaxId(vendor.getTaxId());
        // vendorResponse.setVatNumber(vendor.getVatNumber);
        vendorResponse.setEstablishedYear(vendor.getEstablishedYear());
        vendorResponse.setWebsiteUrl(vendor.getWebsiteUrl());
        vendorResponse.setStreetAddress1(vendor.getStreetAddress1());
        vendorResponse.setStreetAddress2(vendor.getStreetAddress2());
        vendorResponse.setCity(vendor.getCity());
        vendorResponse.setState(vendor.getState());
        vendorResponse.setPostalCode(vendor.getPostalCode());
        vendorResponse.setHaccpCertified(vendor.getHaccpCertified());
        vendorResponse.setHaccpExpiryDate(vendor.getHaccpExpiryDate());
        vendorResponse.setFdaRegistrationNumber(vendor.getFdaRegistrationNumber());
        vendorResponse.setFdaRegistrationExpiry(vendor.getFdaRegistrationExpiry());
        vendorResponse.setIsVeganCertified(vendor.getIsVeganCertified());
        vendorResponse.setVeganCertificationBody(vendor.getVeganCertificationBody());
        vendorResponse.setVeganCertificationExpiry(vendor.getVeganCertificationExpiry());
        vendorResponse.setHasLiabilityInsurance(vendor.getHasLiabilityInsurance());
        vendorResponse.setInsuranceExpiryDate(vendor.getInsuranceExpiryDate());

        /**
            If document URL needed 

            List<String> urls = new ArrayList<>();
            if(vendor.getDocuments() != null){
                vendor.getDocuments().forEach(d -> urls.add(d.getFileUrl()));
            }
            vendorResponse.setDocumentUrls(urls);
        */

        return vendorResponse;
    }

     public List<Vendor> getPendingVendors() {
        return vendorRepository.findVendorsWithPendingApprovals();
    }

    public Vendor getVendorDetails(Long vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    public List<Vendor> findByRegistrationStatusNot(RegistrationStatus approved) {
        return vendorRepository.findByRegistrationStatusNot(approved);    
    }

    //  public List<Orders> getOrdersForVendor(User user) {
    //     Vendor vendor = vendorRepository.findByUser(user)
    //             .orElseThrow(() -> new RuntimeException("Vendor not found"));
    //     return orderRepository.findOrdersByVendorId(vendor.getVendorId());
    // }

public List<OrderResponseDTO> getOrdersForVendor(User user) {
    Vendor vendor = vendorRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Vendor not found"));

    List<Orders> orders = orderRepository.findOrdersByVendorId(vendor.getVendorId());

    return orders.stream().map(order -> new OrderResponseDTO(
            order.getId(),
            order.getUser().getUserName(),   // fetch username
            order.getUser().getEmail(),      // fetch email
            order.getItems().stream()
                    .map(item -> new OrderItemDTO(
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getPriceAtPurchase()
                    ))
                    .collect(Collectors.toList()),
            order.getTotalAmount(),
            order.getOrderDate()
    )).collect(Collectors.toList());
}
//------------ Compilance ----------------//

public List<VendorCompliance> getAllByVendor(Long vendorId) {
        return complianceRepo.findByVendor_VendorId(vendorId);
    }

    public Optional<VendorCompliance> getById(Long id) {
        return complianceRepo.findById(id);
    }

    public VendorCompliance updateStatus(Long id, ComplianceStatus status, String notes, LocalDate expiryDate) {
        VendorCompliance compliance = complianceRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Compliance not found"));

        compliance.setStatus(status);
        if (notes != null) compliance.setNotes(notes);
        if (expiryDate != null) compliance.setExpiryDate(expiryDate);

        return complianceRepo.save(compliance);
    }

    public VendorCompliance saveCompliance(Long vendorId, VendorCompliance compliance) {
        Vendor vendor = vendorRepository.findById(vendorId)
            .orElseThrow(() -> new RuntimeException("Vendor not found"));
            System.out.println("***************** "+vendorId+" **********");
        compliance.setVendor(vendor);
        compliance.setStatus(ComplianceStatus.PENDING);
        return complianceRepo.save(compliance);
    }

    public VendorCompliance saveComplianceFromDTO(Long vendorId, VendorComplianceDTO dto) {
    Vendor vendor = vendorRepository.findById(vendorId).orElseThrow();
    VendorCompliance compliance = new VendorCompliance();
    compliance.setComplianceType(ComplianceType.valueOf(dto.getComplianceType()));
    compliance.setCertificationNumber(dto.getCertificationNumber());
    compliance.setIssuingAuthority(dto.getIssuingAuthority());
    compliance.setIssueDate(dto.getIssueDate());
    compliance.setExpiryDate(dto.getExpiryDate());
    compliance.setNotes(dto.getNotes());
    compliance.setStatus(ComplianceStatus.PENDING);
    compliance.setRequiresRenewal(true);
    compliance.setVendor(vendor);
    return complianceRepo.save(compliance);
}

}
