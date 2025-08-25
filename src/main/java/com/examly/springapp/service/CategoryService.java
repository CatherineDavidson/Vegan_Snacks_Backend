package com.examly.springapp.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.CategoryDto;
import com.examly.springapp.model.Category;
import com.examly.springapp.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public String createCategory(Category category){
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with this name already exists!");
        }

        category.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        category.setNoOfProducts(0);
        categoryRepository.save(category);
        return category.getName()+" created successfully :)";
    }

    public Category updateCategory(CategoryDto category){
        Category existingCategory = categoryRepository.findByName(category.getName())
            .orElseThrow(()->new RuntimeException("Category does not exist"));
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        return categoryRepository.save(existingCategory);
    }

    public String deleteCategory(String name){
        categoryRepository.findByName(name)
            .orElseThrow(()->new RuntimeException("Category does not exist"));
        categoryRepository.deleteByName(name);
        return "Category "+name+" deleted";
    }

    public List<Category> getTop12CategoriesByNoOfProducts() {
        Pageable pageable = PageRequest.of(0, 12, Sort.by(Sort.Direction.DESC, "noOfProducts"));
        return categoryRepository.findAll(pageable).getContent();
    }

    public Category getCategoryById(Long id) {
    return categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
}

public Category getCategoryByName(String name) {
    return categoryRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Category not found with name: " + name));
}
public Page<Category> getCategoriesPaged(int page, int size, String search) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
    if (search == null || search.isBlank()) {
        return categoryRepository.findAll(pageable);
    }
    return categoryRepository.findByNameContainingIgnoreCase(search, pageable);
}
// public Map<String, Object> getCategorySummary() {
//     Map<String, Object> summary = new HashMap<>();
//     summary.put("totalCategories", categoryRepository.count());
//     summary.put("topCategory", categoryRepository.findTopByOrderByNoOfProductsDesc().orElse(null));
//     summary.put("recentCategories", categoryRepository.findTop5ByOrderByCreatedAtDesc());
//     return summary;
// }

   public Map<String, Object> getCategorySummary() {
        Map<String, Object> summary = new HashMap<>();

        // Total categories
        long totalCategories = categoryRepository.count();
        summary.put("totalCategories", totalCategories);

        // Top category by number of products
        Optional<Category> topCategoryOpt = categoryRepository.findAll()
                .stream()
                .max(Comparator.comparingInt(c -> c.getProducts().size()));

        summary.put("topCategory", topCategoryOpt.map(c -> Map.of(
                "name", c.getName(),
                "noOfProducts", c.getProducts().size()
        )).orElse(Map.of("name", "", "noOfProducts", 0)));

        // Categories created this month
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        long newThisMonth = categoryRepository.findAll()
                .stream()
                .filter(c -> c.getCreatedAt() != null &&
                        c.getCreatedAt().toLocalDateTime().toLocalDate().isAfter(firstDayOfMonth.minusDays(1))
                )
                .count();
        summary.put("newThisMonth", newThisMonth);

        return summary;
    }

    public Map<String, Object> getCategoryChartData() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, Object> chartData = new HashMap<>();

        // Bar chart: category name vs number of products
        List<String> categoryNames = categories.stream()
                .map(Category::getName)
                .toList();

        List<Integer> categoryProductCounts = categories.stream()
                .map(c -> (c.getProducts() != null ? c.getProducts().size() : 0)
                       + (c.getSnacks() != null ? c.getSnacks().size() : 0))
                .toList();

        // Line chart: categories created per day (last 30 days)
        LocalDate today = LocalDate.now();
        Map<String, Long> categoriesByDate = new TreeMap<>();
        for (int i = 0; i < 30; i++) {
            LocalDate day = today.minusDays(i);
            long count = categories.stream()
                    .filter(c -> c.getCreatedAt().toLocalDateTime().toLocalDate().isEqual(day))
                    .count();
            categoriesByDate.put(day.toString(), count);
        }

        chartData.put("categoryNames", categoryNames);
        chartData.put("categoryProductCounts", categoryProductCounts);
        chartData.put("categoriesByDate", categoriesByDate);

        return chartData;
    }
}
