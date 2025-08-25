package com.examly.springapp.controller;

import com.examly.springapp.dto.product.ProductResponseDTO;
import com.examly.springapp.enums.ComplianceStatus;
import com.examly.springapp.enums.ProductStatus;
import com.examly.springapp.model.product.Product;
import com.examly.springapp.model.vendor.VendorCompliance;
import com.examly.springapp.service.DocumentService;
import com.examly.springapp.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final DocumentService documentService; // reuse for compliance

    // // List pending products
    // @PreAuthorize("hasRole('ADMIN')")
    // @GetMapping("/pending")
    // public ResponseEntity<List<Product>> listPending() {
    //     return ResponseEntity.ok(productService.listApproved()
    //             .stream().filter(p -> p.getStatus() == ProductStatus.PENDING_APPROVAL).toList());
    // }

    // Change product status
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}/status")
    public ResponseEntity<Product> setStatus(@PathVariable Long productId, @RequestParam ProductStatus status) {
        return ResponseEntity.ok(productService.setStatus(productId, status));
    }

    // Update compliance status via existing documentService
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/compliance/{complianceId}/status")
    public ResponseEntity<VendorCompliance> updateComplianceStatus(@PathVariable Long complianceId,
                                                                   @RequestParam ComplianceStatus status,
                                                                   @RequestParam(required = false) String notes) {
        return ResponseEntity.ok(documentService.updateComplianceStatus(complianceId, status, notes, null));
    }

@GetMapping("/")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
    List<ProductResponseDTO> list = productService.getAllProduct()
            .stream()
            .map(ProductResponseDTO::fromEntity) 
            .toList();
    return ResponseEntity.ok(list);
}


}
