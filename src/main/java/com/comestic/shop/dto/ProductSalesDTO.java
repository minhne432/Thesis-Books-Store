package com.comestic.shop.dto;

public class ProductSalesDTO {
    private String productName;
    private Long totalQuantity;

    public ProductSalesDTO(String productName, Long totalQuantity) {
        this.productName = productName;
        this.totalQuantity = totalQuantity;
    }

    // Getters và Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
