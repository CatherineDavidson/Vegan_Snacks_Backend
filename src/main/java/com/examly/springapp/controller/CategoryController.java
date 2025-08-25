package com.examly.springapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.CategoryDto;
import com.examly.springapp.model.Category;
import com.examly.springapp.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    
    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_MANAGER','CUSTOMER','VENDOR')")
    @GetMapping("/all")
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @PreAuthorize("hasAnyRole('PRODUCT_MANAGER','ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryDto category){
        Category updatedCategory = categoryService.updateCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @PreAuthorize("hasAnyRole('PRODUCT_MANAGER','ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam String name){
        return ResponseEntity.ok(categoryService.deleteCategory(name));
    }

    // @PreAuthorize("hasAnyRole('PRODUCT_MANAGER','ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> createCategory (@RequestBody Category category){
                System.out.println("**********************************");
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @GetMapping("/topCategory")
    public List<Category> getTopCategories (){
        return categoryService.getTop12CategoriesByNoOfProducts();
    }
@PreAuthorize("hasAnyRole('ADMIN','PRODUCT_MANAGER','CUSTOMER','VENDOR')")
@GetMapping("/paged")
public Page<Category> getCategoriesPaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "") String search
) {
    return categoryService.getCategoriesPaged(page, size, search);
}
@PreAuthorize("hasAnyRole('ADMIN','PRODUCT_MANAGER','CUSTOMER','VENDOR')")
@GetMapping("/summary")
public Map<String, Object> getCategorySummary() {
    return categoryService.getCategorySummary();
}



    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_MANAGER','CUSTOMER','VENDOR')")
@GetMapping("/{id}")
public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
    return ResponseEntity.ok(categoryService.getCategoryById(id));
}

@PreAuthorize("hasAnyRole('ADMIN','PRODUCT_MANAGER','CUSTOMER','VENDOR')")
@GetMapping("/byName/{name}")
public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
    return ResponseEntity.ok(categoryService.getCategoryByName(name));
}

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_MANAGER')")
    @GetMapping("/chart-data")
    public ResponseEntity<Map<String, Object>> getCategoryChartData() {
        Map<String, Object> chartData = categoryService.getCategoryChartData();
        return ResponseEntity.ok(chartData);
    }


    // @GetMapping("/CategorySummary")
    // publis List<
}
