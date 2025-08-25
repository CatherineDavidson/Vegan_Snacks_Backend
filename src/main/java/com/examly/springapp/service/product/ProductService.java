// package com.examly.springapp.service.product;

// import com.examly.springapp.dto.product.ProductCreateDTO;
// import com.examly.springapp.dto.product.ProductUpdateDTO;
// import com.examly.springapp.enums.ProductStatus;
// import com.examly.springapp.model.product.Product;
// import com.examly.springapp.model.vendor.Vendor;
// import com.examly.springapp.repository.product.ProductRepository;
// import com.examly.springapp.service.product.ProductService;
// import com.examly.springapp.service.VendorService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import java.util.List;

// @Service
// @RequiredArgsConstructor
// public class ProductService{

//     private final ProductRepository productRepository;
//     private final VendorService vendorService;

//     @Transactional
//     public Product create(ProductCreateDTO dto) {
//         Vendor vendor = vendorService.getVendorDetails(dto.getVendorId());
//         Product p = new Product();
//         p.setVendor(vendor);
//         p.setName(dto.getName());
//         p.setDescription(dto.getDescription());
//         p.setIngredients(dto.getIngredients());
//         p.setNutritionInfo(dto.getNutritionInfo());
//         p.setAllergens(dto.getAllergens());
//         p.setWeight(dto.getWeight());
//         p.setPackageSize(dto.getPackageSize());
//         p.setShelfLife(dto.getShelfLife());
//         p.setStorageInstructions(dto.getStorageInstructions());
//         p.setStatus(ProductStatus.DRAFT);
//         p.setImageUrl(dto.getImageUrl());
//         return productRepository.save(p);
//     }

//     @Transactional
//     public Product update(Long productId, ProductUpdateDTO dto) {
//         Product p = get(productId);
//         if (dto.getName() != null) p.setName(dto.getName());
//         if (dto.getDescription() != null) p.setDescription(dto.getDescription());
//         if (dto.getIngredients() != null) p.setIngredients(dto.getIngredients());
//         if (dto.getNutritionInfo() != null) p.setNutritionInfo(dto.getNutritionInfo());
//         if (dto.getAllergens() != null) p.setAllergens(dto.getAllergens());
//         if (dto.getWeight() != null) p.setWeight(dto.getWeight());
//         if (dto.getPackageSize() != null) p.setPackageSize(dto.getPackageSize());
//         if (dto.getShelfLife() != null) p.setShelfLife(dto.getShelfLife());
//         if (dto.getStorageInstructions() != null) p.setStorageInstructions(dto.getStorageInstructions());
//         if (dto.getImageUrl() != null) p.setImageUrl(dto.getImageUrl());
//         return productRepository.save(p);
//     }

//     @Transactional(readOnly = true)
//     public Product get(Long productId) {
//         return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
//     }

//     @Transactional(readOnly = true)
//     public List<Product> listByVendor(Long vendorId) {
//         return productRepository.findByVendorVendorId(vendorId);
//     }

//     @Transactional(readOnly = true)
//     public List<Product> listApproved() {
//         return productRepository.findByStatus(ProductStatus.APPROVED);
//     }

//     @Transactional
//     public Product setStatus(Long productId, ProductStatus status) {
//         Product p = get(productId);
//         p.setStatus(status);
//         return productRepository.save(p);
//     }

//     public void deleteProduct(Long productId) {
//         Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product no found"));
//         productRepository.delete(product);
//     }
// }

package com.examly.springapp.service.product;

import com.examly.springapp.dto.product.ProductCreateDTO;
import com.examly.springapp.dto.product.ProductResponseDTO;
import com.examly.springapp.dto.product.ProductUpdateDTO;
import com.examly.springapp.enums.ProductStatus;
import com.examly.springapp.model.Category;
import com.examly.springapp.model.product.Product;
import com.examly.springapp.model.vendor.Vendor;
import com.examly.springapp.repository.CategoryRepository;
import com.examly.springapp.repository.product.ProductRepository;
import com.examly.springapp.service.VendorService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final VendorService vendorService;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product create(ProductCreateDTO dto) {
        // Get vendor
        Vendor vendor = vendorService.getVendorDetails(dto.getVendorId());
        
        // Get category
        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));
        
        Product product = new Product();
        product.setVendor(vendor);
        product.setCategory(category);
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setIngredients(dto.getIngredients());
        product.setNutritionInfo(dto.getNutritionInfo());
        product.setAllergens(dto.getAllergens());
        product.setWeight(dto.getWeight());
        product.setPackageSize(dto.getPackageSize());
        product.setShelfLife(dto.getShelfLife());
        product.setStorageInstructions(dto.getStorageInstructions());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setStatus(ProductStatus.APPROVED); // Set to ACTIVE instead of DRAFT
        product.setImageUrl(dto.getImageUrl());
        
        return productRepository.save(product);
    }

    public List<Product> getAllProduct() {
    return productRepository.findAll(); // assuming you have a ProductRepository
}


    @Transactional
    public Product update(Long productId, ProductUpdateDTO dto) {
        Product product = get(productId);
        
        // Update all fields if they are provided
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            product.setName(dto.getName());
        }
        if (dto.getDescription() != null && !dto.getDescription().trim().isEmpty()) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getIngredients() != null && !dto.getIngredients().trim().isEmpty()) {
            product.setIngredients(dto.getIngredients());
        }
        if (dto.getNutritionInfo() != null && !dto.getNutritionInfo().trim().isEmpty()) {
            product.setNutritionInfo(dto.getNutritionInfo());
        }
        if (dto.getAllergens() != null && !dto.getAllergens().trim().isEmpty()) {
            product.setAllergens(dto.getAllergens());
        }
        if (dto.getWeight() != null) {
            product.setWeight(dto.getWeight());
        }
        if (dto.getPackageSize() != null && !dto.getPackageSize().trim().isEmpty()) {
            product.setPackageSize(dto.getPackageSize());
        }
        if (dto.getShelfLife() != null) {
            product.setShelfLife(dto.getShelfLife());
        }
        if (dto.getStorageInstructions() != null && !dto.getStorageInstructions().trim().isEmpty()) {
            product.setStorageInstructions(dto.getStorageInstructions());
        }
        // if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        // }
        if (dto.getStock() != null) {
            product.setStock(dto.getStock());
        }
        if (dto.getImageUrl() != null && !dto.getImageUrl().trim().isEmpty()) {
            product.setImageUrl(dto.getImageUrl());
        }
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
public List<ProductCreateDTO> listApprovedDTO() {
    return productRepository.findByStatus(ProductStatus.APPROVED)
        .stream()
        .map(ProductCreateDTO::new)
        .toList();
}


    @Transactional(readOnly = true)
    public Product get(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional(readOnly = true)
    public List<Product> listByVendor(Long vendorId) {
        return productRepository.findByVendorVendorId(vendorId);
    }

    // @Transactional(readOnly = true)
    // public List<Product> listApproved() {
    //     return productRepository.findByStatus(ProductStatus.APPROVED);
    // }

    @Transactional(readOnly = true)
    public List<Product> listActive() {
        return productRepository.findByStatus(ProductStatus.APPROVED);
    }

    @Transactional
    public Product setStatus(Long productId, ProductStatus status) {
        Product product = get(productId);
        product.setStatus(status);
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    // @Transactional
    // public Product updateStock(Long productId, Integer newStock) {
    //     Product product = get(productId);
    //     product.setStock(newStock);
        
    //     // Update status based on stock
    //     if (newStock <= 0) {
    //         product.setStatus(ProductStatus.OUT_OF_STOCK);
    //     } else if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
    //         product.setStatus(ProductStatus.ACTIVE);
    //     }
        
    //     return productRepository.save(product);
    // }

    // @Transactional
    // public Product updatePrice(Long productId, double newPrice) {
    //     Product product = get(productId);
    //     product.setPrice(newPrice);
    //     return productRepository.save(product);
    // }

     public List<Product> getAllProducts(String search, String category) {
        if (search != null && category != null) {
            return productRepository.findByNameContainingIgnoreCaseAndCategory_NameIgnoreCase(search, category);
        } else if (search != null) {
            return productRepository.findByNameContainingIgnoreCase(search);
        } else if (category != null) {
            return productRepository.findByCategory_NameIgnoreCase(category);
        } else {
            return productRepository.findAll();
        }
    }

    // public Product getProductById(Long productId) {
    //     return productRepository.findById(productId)
    //             .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
    // }

    //  @Override
    // public Product get(Long productId) {
    //     return productRepository.findById(productId)
    //             .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + productId));
    // }

    public Page<Product> getAll(String q, String category, Pageable pageable) {
        if (q != null && category != null) {
            return productRepository.findByNameContainingIgnoreCaseAndCategory_NameIgnoreCase(q, category, pageable);
        } else if (q != null) {
            return productRepository.findByNameContainingIgnoreCase(q, pageable);
        } else if (category != null) {
            return productRepository.findByCategory_NameIgnoreCase(category, pageable);
        }
        return productRepository.findAll(pageable);
    } @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> listApproved() {
        return productRepository.findByStatus(ProductStatus.APPROVED)
                .stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
    }
}