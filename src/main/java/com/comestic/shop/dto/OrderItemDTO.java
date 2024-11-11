package com.comestic.shop.dto;

public class OrderItemDTO {
    private Integer productId;
    private Integer quantity;

    // Getters và Setters

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
