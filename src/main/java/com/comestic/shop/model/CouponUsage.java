package com.comestic.shop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class CouponUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int couponUsageID;

    // Quan hệ Many-to-One với Coupon
    @ManyToOne
    @JoinColumn(name = "couponID", nullable = false)
    private Coupon coupon;

    // Quan hệ Many-to-One với Customer
    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    private Customer customer;

    // Quan hệ Many-to-One với Order
    @ManyToOne
    @JoinColumn(name = "orderID", nullable = false)
    private Order order;

    @Temporal(TemporalType.TIMESTAMP)
    private Date redemptionDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount;

    // Getters và Setters

    public int getCouponUsageID() {
        return couponUsageID;
    }

    public void setCouponUsageID(int couponUsageID) {
        this.couponUsageID = couponUsageID;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getRedemptionDate() {
        return redemptionDate;
    }

    public void setRedemptionDate(Date redemptionDate) {
        this.redemptionDate = redemptionDate;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}
