package com.examly.springapp.controller;

import com.examly.springapp.dto.OrderResponseDTO;
import com.examly.springapp.dto.product.*;
import com.examly.springapp.model.Orders;
import com.examly.springapp.model.User;
import com.examly.springapp.model.product.Product;
import com.examly.springapp.model.product.ProductCertification;
import com.examly.springapp.model.product.ProductInventory;
import com.examly.springapp.service.ImageUploadService;
import com.examly.springapp.service.VendorService;
import com.examly.springapp.service.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/vendor/products")
@RequiredArgsConstructor
public class VendorProductController {

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final CertificationService certificationService;
    private final ImageUploadService imageUploadService;
    private final VendorService vendorService;

    @PostMapping
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<Product> create(
            @RequestPart("product") ProductCreateDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = imageUploadService.upload(file);
            dto.setImageUrl(imageUrl);
        }

        return ResponseEntity.ok(productService.create(dto));
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<Product> update(
            @PathVariable Long productId,
            @RequestPart("product") ProductUpdateDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        if (file != null && !file.isEmpty()) {
            String imageUrl = imageUploadService.upload(file);
            dto.setImageUrl(imageUrl);
        }

        return ResponseEntity.ok(productService.update(productId, dto));
    }


    // List vendor products
    @PreAuthorize("hasRole('VENDOR')")
    @GetMapping
    public ResponseEntity<List<Product>> listByVendor(@RequestParam Long vendorId) {
        return ResponseEntity.ok(productService.listByVendor(vendorId));
    }

    // Inventory add & list
    @PreAuthorize("hasRole('VENDOR')")
    @PostMapping("/{productId}/inventory")
    public ResponseEntity<ProductInventory> addInventory(@PathVariable Long productId, @RequestBody InventoryDTO dto) {
        return ResponseEntity.ok(inventoryService.add(productId, dto));
    }

    @PreAuthorize("hasRole('VENDOR')")
    @GetMapping("/{productId}/inventory")
    public ResponseEntity<List<ProductInventory>> listInventory(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.list(productId));
    }

    // Certifications
    @PreAuthorize("hasRole('VENDOR')")
    @PostMapping("/{productId}/certifications")
    public ResponseEntity<ProductCertification> addCert(@PathVariable Long productId, @RequestBody CertificationDTO dto) {
        return ResponseEntity.ok(certificationService.add(productId, dto));
    }

    @PreAuthorize("hasRole('VENDOR')")
    @GetMapping("/{productId}/certifications")
    public ResponseEntity<List<ProductCertification>> listCerts(@PathVariable Long productId) {
        return ResponseEntity.ok(certificationService.list(productId));
    }
    
    @PreAuthorize("hasRole('VENDOR')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted Successfully");
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<List<OrderResponseDTO>> getVendorOrders() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(vendorService.getOrdersForVendor(user));
    }

}
