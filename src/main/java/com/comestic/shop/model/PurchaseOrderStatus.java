package com.comestic.shop.model;

public enum PurchaseOrderStatus {
    PENDING("Pending", "Chờ xử lý"),
    CONFIRMED("Confirmed", "Đã xác nhận"),
    PROCESSING("Processing", "Đang xử lý"),
    SHIPPED("Shipped", "Đã giao hàng"),
    RECEIVED("Received", "Đã nhận hàng"),
    CANCELLED("Cancelled", "Đã hủy");

    private final String status;
    private final String description;

    PurchaseOrderStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
