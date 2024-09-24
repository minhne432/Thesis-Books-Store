package com.comestic.shop.model;

import com.comestic.shop.model.Supplier;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PURCHASE_ORDER")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PurchaseOrderID")
    private Long purchaseOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierID", nullable = false)
    private Supplier supplier;

    @Column(name = "OrderDate", nullable = false)
    private LocalDate orderDate;

    @Column(name = "TotalAmount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "Status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PurchaseOrderDetails> purchaseOrderDetails = new ArrayList<>();

    // Constructors
    public PurchaseOrder() {}

    public PurchaseOrder(Supplier supplier, LocalDate orderDate, BigDecimal totalAmount, String status) {
        this.supplier = supplier;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getters and Setters
    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PurchaseOrderDetails> getPurchaseOrderDetails() {
        return purchaseOrderDetails;
    }

    public void setPurchaseOrderDetails(List<PurchaseOrderDetails> purchaseOrderDetails) {
        this.purchaseOrderDetails = purchaseOrderDetails;
    }

    // Helper methods
    public void addPurchaseOrderDetail(PurchaseOrderDetails detail) {
        purchaseOrderDetails.add(detail);
        detail.setPurchaseOrder(this);
    }

    public void removePurchaseOrderDetail(PurchaseOrderDetails detail) {
        purchaseOrderDetails.remove(detail);
        detail.setPurchaseOrder(null);
    }

    public void updateTotalAmount() {
        this.totalAmount = purchaseOrderDetails.stream()
                .map(PurchaseOrderDetails::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // You might want to add equals(), hashCode(), and toString() methods here
    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "purchaseOrderId=" + purchaseOrderId +
                ", supplier='" + supplier + '\'' +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                '}';
    }
}
