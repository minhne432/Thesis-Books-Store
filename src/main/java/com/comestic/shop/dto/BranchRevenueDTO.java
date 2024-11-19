package com.comestic.shop.dto;

import java.math.BigDecimal;

public class BranchRevenueDTO {
    private String branchName;
    private BigDecimal totalRevenue;

    public BranchRevenueDTO(String branchName, BigDecimal totalRevenue) {
        this.branchName = branchName;
        this.totalRevenue = totalRevenue;
    }

    // Getters và Setters
    public String getBranchName() {
        return branchName;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }
}
