package com.examly.springapp.controller;

import com.examly.springapp.dto.VendorComplianceDTO;
import com.examly.springapp.dto.VendorRegistrationDTO;
import com.examly.springapp.dto.VendorResponseDTO;
import com.examly.springapp.enums.ComplianceStatus;
import com.examly.springapp.enums.RegistrationStatus;
import com.examly.springapp.enums.VerificationStatus;
import com.examly.springapp.model.vendor.Vendor;
import com.examly.springapp.model.vendor.VendorCompliance;
import com.examly.springapp.model.vendor.VendorDocument;
import com.examly.springapp.service.DocumentService;
import com.examly.springapp.service.VendorService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/vendor")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;
    private final DocumentService documentService;

    // @PostMapping("/register")
    // public ResponseEntity<Vendor> registerVendor(@ModelAttribute VendorRegistrationDTO dto) {
    //     try {
    //         Vendor savedVendor = vendorService.registerVendor(dto);
    //         return ResponseEntity.ok(savedVendor);
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(null);
    //     }
    // }

    @PreAuthorize("hasRole('VENDOR')")
    @PostMapping("/upload-document")
    public String uploadDocument(@RequestParam String documentType,
                                @RequestParam MultipartFile file,
                                @RequestParam(defaultValue = "false") boolean overwrite) throws Exception{
        return documentService.uploadVendorDocument(documentType, file, overwrite);
    }

    @GetMapping("/document-exists")
    public boolean checkDocumentExists(@RequestParam String type) {
        return documentService.documentExists(type);
    }

    @PreAuthorize("hasRole('VENDOR')")
    @DeleteMapping("/delete-document/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        documentService.deleteVendorDocument(id);
        return ResponseEntity.ok("Document deleted successfully");
    }

    @PreAuthorize("hasRole('VENDOR')")
    @PutMapping("/updateProfile")
    public ResponseEntity<String> updateVendorProfile(@RequestBody VendorRegistrationDTO dto){
        vendorService.updateVendorProfile(dto);
        return ResponseEntity.ok("Profile Updated Successfully");
    }

    @GetMapping("/profile")
    public VendorResponseDTO getVendorProfile(){
        return vendorService.getVendorProfile();
    }

    @GetMapping("/uploadedDocs")
    public List<VendorDocument> getAllUploadedDocs(){
        return documentService.getAllUploadedDocs();
    }

    // @PutMapping("/document/{docId}/status")
    // public VendorDocument updateDocumentStatus(
    //     @PathVariable Long docId,
    //     @RequestParam VerificationStatus status,
    //     @RequestParam(required = false) String remarks,
    //     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiryDate) {
    //     return documentService.updateDocumentStatus(docId, status, remarks, expiryDate);
    // }

    // @GetMapping("/pending-approvals")
    // public List<Vendor> getPendingVendors() {
    //     return vendorService.getPendingVendors();
    // }

    @GetMapping("/{vendorId}")
    public Vendor getVendorDetails(@PathVariable Long vendorId) {
        return vendorService.getVendorDetails(vendorId);
    }

    // @PutMapping("/compliance/{complianceId}/status")
    // public VendorCompliance updateComplianceStatus(
    //         @PathVariable Long complianceId,
    //         @RequestParam ComplianceStatus status,
    //         @RequestParam(required = false) String notes,
    //                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiryDate) {
    //     return documentService.updateComplianceStatus(complianceId, status, notes,expiryDate);
    // }
     // list all vendors not approved
    @GetMapping("/review-list")
    public List<Vendor> reviewList() {
        return vendorService.findByRegistrationStatusNot(RegistrationStatus.APPROVED);
    }

    // // vendor detail for admin
    // @GetMapping("/{vendorId}")
    // public ResponseEntity<Vendor> getVendor(@PathVariable Long vendorId) {
    //     return vendorRepository.findById(vendorId)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }

    // update document status with remarks + expiry
    @PutMapping("/document/{docId}/status")
    public ResponseEntity<VendorDocument> updateDocStatus(
            @PathVariable Long docId,
            @RequestParam VerificationStatus status,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiryDate
    ) {
        VendorDocument updated = documentService.updateDocumentStatus(docId, status, remarks, expiryDate);
        return ResponseEntity.ok(updated);
    }
    
    @PostMapping("/{vendorId}/compliance")
    public ResponseEntity<VendorCompliance> addCompliance(
            @PathVariable Long vendorId,
            @RequestBody VendorComplianceDTO dto
    ) {
        VendorCompliance saved = vendorService.saveComplianceFromDTO(vendorId, dto);
        return ResponseEntity.ok(saved);
    }

    // update compliance status (optional notes + expiryDate support)
    @PutMapping("/compliance/{complianceId}/status")
    public ResponseEntity<VendorCompliance> updateComplianceStatus(
            @PathVariable Long complianceId,
            @RequestParam ComplianceStatus status,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiryDate
    ) {
        VendorCompliance updated = documentService.updateComplianceStatus(complianceId, status, notes, expiryDate);
        return ResponseEntity.ok(updated);
    }

        // Get all compliance for a vendor
    @GetMapping("/compliance/{vendorId}")
    public ResponseEntity<List<VendorCompliance>> getVendorCompliance(@PathVariable Long vendorId) {
        List<VendorCompliance> list = vendorService.getAllByVendor(vendorId);
        return ResponseEntity.ok(list);
    }


    @PutMapping("/compilance/{complianceId}/status")
    public ResponseEntity<VendorCompliance> updateStatus(
            @PathVariable Long complianceId,
            @RequestParam ComplianceStatus status,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) String expiryDate // optional in yyyy-MM-dd
    ) {
        LocalDate expiry = null;
        if (expiryDate != null) {
            expiry = LocalDate.parse(expiryDate);
        }
        VendorCompliance updated = vendorService.updateStatus(complianceId, status, notes, expiry);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/stats")
public ResponseEntity<Map<String, Integer>> getVendorStats() {
    List<Vendor> allVendors = vendorService.getAllVendors(); // implement this in service
    List<Vendor> pendingVendors = vendorService.findByRegistrationStatusNot(RegistrationStatus.APPROVED);

    Map<String, Integer> stats = new HashMap<>();
    stats.put("totalVendors", allVendors.size());
    stats.put("pendingApprovals", pendingVendors.size());
    stats.put("activeVendors", allVendors.size() - pendingVendors.size());

    return ResponseEntity.ok(stats);
}

}
