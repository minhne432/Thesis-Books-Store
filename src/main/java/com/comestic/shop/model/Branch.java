package com.comestic.shop.model;

import com.comestic.shop.model.Inventory;
import com.comestic.shop.model.Order;
import jakarta.persistence.*;
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

    @Column(name = "ContactNumber")
    private String contactNumber;

    @Column(name = "Manager")
    private String manager;

    @ManyToOne
    @JoinColumn(name = "AddressID", referencedColumnName = "AddressID", nullable = false)
    private Address address;  // Added relationship to Address

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventories = new ArrayList<>();

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>(); // Purchase Orders


    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BranchDistance> branchDistances = new ArrayList<>();

    // Getters and Setters
    public List<BranchDistance> getBranchDistances() {
        return branchDistances;
    }

    public void setBranchDistances(List<BranchDistance> branchDistances) {
        this.branchDistances = branchDistances;
    }

    // Helper methods
    public void addBranchDistance(BranchDistance branchDistance) {
        branchDistances.add(branchDistance);
        branchDistance.setBranch(this);
    }

    public void removeBranchDistance(BranchDistance branchDistance) {
        branchDistances.remove(branchDistance);
        branchDistance.setBranch(null);
    }


    // Constructors
    public Branch() {}

    public Branch(String branchName, String contactNumber, String manager, Address address) {
        this.branchName = branchName;
        this.contactNumber = contactNumber;
        this.manager = manager;
        this.address = address;  // Initialize address
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    // Methods to manage Purchase Orders
    public void addPurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrders.add(purchaseOrder);
        purchaseOrder.setBranch(this);
    }

    public void removePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrders.remove(purchaseOrder);
        purchaseOrder.setBranch(null);
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    // equals(), hashCode(), and toString() methods
    @Override
    public String toString() {
        return "Branch{" +
                "branchID=" + branchId +
                ", branchName='" + branchName + '\'' +
                ", manager='" + manager + '\'' +
                ", address=" + address +  // Include address in toString
                '}';
    }
}
