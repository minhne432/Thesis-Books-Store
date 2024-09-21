package com.comestic.shop.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "INVENTORY")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InventoryID")
    private Long inventoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BranchID", nullable = false)
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "LastUpdatedDate", nullable = false)
    private LocalDate lastUpdatedDate;

    // Constructors
    public Inventory() {}

    public Inventory(Branch branch, Product product, Integer quantity, LocalDate lastUpdatedDate) {
        this.branch = branch;
        this.product = product;
        this.quantity = quantity;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    // Getters and Setters
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    // Helper methods
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        this.lastUpdatedDate = LocalDate.now();
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
        this.lastUpdatedDate = LocalDate.now();
    }

    public void decreaseQuantity(int amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
            this.lastUpdatedDate = LocalDate.now();
        } else {
            throw new IllegalArgumentException("Not enough inventory");
        }
    }

    // You might want to add equals(), hashCode(), and toString() methods here
}
