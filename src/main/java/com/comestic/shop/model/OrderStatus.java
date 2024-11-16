package com.comestic.shop.model;

public enum OrderStatus {
    NEW,
    UNPAID_PENDING,
    PAID_PENDING,
    PAID_PACKED,
    PAID_SHIPPED,
    UNPAID_SHIPPED,
    UNPAID_CANCELED,
    PAID_CANCELED,
    DELIVERED,
    PENDING
}