package com.comestic.shop.model;

import com.comestic.shop.model.PurchaseOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SUPPLIER")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupplierID")
    private Long supplierId;

    @Column(name = "SupplierName", nullable = false)
    private String supplierName;

    @Column(name = "ContactPerson")
    private String contactPerson;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Email")
    private String email;

    @Column(name = "Address")
    private String address;

    @OneToMany(mappedBy = "supplier", orphanRemoval = true)
    @JsonManagedReference // Đây là phía "quản lý" của mối quan hệ
    @JsonIgnore
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    // Constructors
    public Supplier() {}

    public Supplier(String supplierName, String contactPerson, String phone, String email, String address) {
        this.supplierName = supplierName;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // Getters and Setters
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    // Helper methods
    public void addPurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrders.add(purchaseOrder);
        purchaseOrder.setSupplier(this);
    }

    public void removePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrders.remove(purchaseOrder);
        purchaseOrder.setSupplier(null);
    }

    // You might want to add equals(), hashCode(), and toString() methods here
}
