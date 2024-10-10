package com.comestic.shop.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderDTO {
    private Long supplierId;
    private Long branchId;
    private LocalDate orderDate;
    private String status;
    private List<PurchaseOrderDetailDTO> purchaseOrderDetails = new ArrayList<>();

    // Getters and Setters

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PurchaseOrderDetailDTO> getPurchaseOrderDetails() {
        return purchaseOrderDetails;
    }

    public void setPurchaseOrderDetails(List<PurchaseOrderDetailDTO> purchaseOrderDetails) {
        this.purchaseOrderDetails = purchaseOrderDetails;
    }
}
