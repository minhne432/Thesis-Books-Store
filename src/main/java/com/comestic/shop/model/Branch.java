package com.comestic.shop.model;

import com.comestic.shop.model.Inventory;
import com.comestic.shop.model.Order;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BRANCH")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BranchID")
    private Long branchId;

    @Column(name = "BranchName", nullable = false)
    private String branchName;

    @Column(name = "Location", nullable = false)
    private String location;

    @Column(name = "Latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "Longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "ContactNumber")
    private String contactNumber;

    @Column(name = "Manager")
    private String manager;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventories = new ArrayList<>();

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // Constructors
    public Branch() {}

    public Branch(String branchName, String location, BigDecimal latitude, BigDecimal longitude, String contactNumber, String manager) {
        this.branchName = branchName;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactNumber = contactNumber;
        this.manager = manager;
    }

    // Getters and Setters
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // Helper methods for bidirectional relationships
    public void addInventory(Inventory inventory) {
        inventories.add(inventory);
        inventory.setBranch(this);
    }

    public void removeInventory(Inventory inventory) {
        inventories.remove(inventory);
        inventory.setBranch(null);
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setBranch(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setBranch(null);
    }

    // You might want to add equals(), hashCode(), and toString() methods here
}
