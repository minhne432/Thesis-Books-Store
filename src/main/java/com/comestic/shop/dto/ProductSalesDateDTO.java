package com.comestic.shop.dto;

import java.util.Date;

public class ProductSalesDateDTO {
    private Date  date;
    private String productName;
    private Long totalQuantity;

    public ProductSalesDateDTO(Date  date, String productName, Long totalQuantity) {
        this.date = date;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
    }

    // Getters và Setters


    public Date  getDate() {
        return date;
    }

    public void setDate(Date  date) {
        this.date = date;
    }

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
