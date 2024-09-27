package com.comestic.shop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "COUPON")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CouponID")
    private Long couponId;

    @Column(name = "Code", nullable = false, unique = true)
    private String code;

    @Column(name = "Description")
    private String description;

    @Column(name = "DiscountType", nullable = false)
    private String discountType;

    @Column(name = "DiscountValue", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "MinimumOrderValue", precision = 10, scale = 2)
    private BigDecimal minimumOrderValue;

    @Column(name = "MaxUsageLimit")
    private Integer maxUsageLimit;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private List<CouponUsage> couponUsages;

    public Boolean getActive() {
        return isActive;
    }

    public List<CouponUsage> getCouponUsages() {
        return couponUsages;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setCouponUsages(List<CouponUsage> couponUsages) {
        this.couponUsages = couponUsages;
    }

    // Constructors
    public Coupon() {}

    public Coupon(String code, String description, String discountType, BigDecimal discountValue,
                  LocalDate startDate, LocalDate endDate, BigDecimal minimumOrderValue,
                  Integer maxUsageLimit, Boolean isActive) {
        this.code = code;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minimumOrderValue = minimumOrderValue;
        this.maxUsageLimit = maxUsageLimit;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getMinimumOrderValue() {
        return minimumOrderValue;
    }

    public void setMinimumOrderValue(BigDecimal minimumOrderValue) {
        this.minimumOrderValue = minimumOrderValue;
    }

    public Integer getMaxUsageLimit() {
        return maxUsageLimit;
    }

    public void setMaxUsageLimit(Integer maxUsageLimit) {
        this.maxUsageLimit = maxUsageLimit;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    // Helper methods
    public boolean isValid(LocalDate currentDate, BigDecimal orderTotal) {
        return isActive &&
                currentDate.compareTo(startDate) >= 0 &&
                currentDate.compareTo(endDate) <= 0 &&
                (minimumOrderValue == null || orderTotal.compareTo(minimumOrderValue) >= 0);
    }

    public BigDecimal applyDiscount(BigDecimal orderTotal) {
        if ("PERCENTAGE".equals(discountType)) {
            return orderTotal.multiply(BigDecimal.ONE.subtract(discountValue.divide(new BigDecimal("100"))));
        } else if ("FIXED".equals(discountType)) {
            return orderTotal.subtract(discountValue);
        }
        return orderTotal;
    }

    // You might want to add equals(), hashCode(), and toString() methods here
}
