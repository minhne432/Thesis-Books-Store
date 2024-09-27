package com.comestic.shop.model;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressID;

    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "wardID", nullable = false)
    private Ward ward;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private boolean isDefault;

    // Getters and Setters

    public int getAddressID() {
        return addressID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Ward getWard() {
        return ward;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
