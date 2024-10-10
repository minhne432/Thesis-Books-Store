package com.comestic.shop.dto;

public class BranchDTO {
    private Long branchId;
    private String branchName;
    // Constructors, getters, setters


    public BranchDTO(Long branchId, String branchName) {
        this.branchId = branchId;
        this.branchName = branchName;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
