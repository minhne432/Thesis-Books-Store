package com.comestic.shop.service;

import com.comestic.shop.model.Category;
import com.comestic.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        // Throw an exception or handle the case when the category is not found
        return categoryOptional.orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
    }


}
