package com.comestic.shop.dto;

public class CategorySalesDTO {
    private String categoryName;
    private Long totalQuantity; // Hoặc Double nếu cần

    public CategorySalesDTO(String categoryName, Long totalQuantity) {
        this.categoryName = categoryName;
        this.totalQuantity = totalQuantity;
    }

    // Getters và Setters
    public String getCategoryName() {
        return categoryName;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }
}
