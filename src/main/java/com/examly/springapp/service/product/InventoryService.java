package com.examly.springapp.service.product;

import com.examly.springapp.dto.product.InventoryDTO;
import com.examly.springapp.model.product.Product;
import com.examly.springapp.model.product.ProductInventory;
import com.examly.springapp.repository.product.ProductInventoryRepository;
import com.examly.springapp.service.product.InventoryService;
import com.examly.springapp.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService{

    private final ProductInventoryRepository inventoryRepository;
    private final ProductService productService;

    @Transactional
    public ProductInventory add(Long productId, InventoryDTO dto) {
        Product p = productService.get(productId);
        ProductInventory inv = new ProductInventory();
        inv.setProduct(p);
        inv.setQuantity(dto.getQuantity());
        inv.setExpiryDate(dto.getExpiryDate());
        inv.setBatchNumber(dto.getBatchNumber());
        return inventoryRepository.save(inv);
    }

    @Transactional(readOnly = true)
    public List<ProductInventory> list(Long productId) {
        return inventoryRepository.findByProductProductId(productId);
    }
}
