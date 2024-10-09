package com.comestic.shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AddressID")
    private int addressID;

    @ManyToOne
    @JoinColumn(name = "WardID", nullable = false)
    private Ward ward;

    @Column(name = "StreetAddress", nullable = false)
    private String streetAddress;

    @Column(name = "PostalCode", nullable = false)
    private String postalCode;

    @Column(name = "IsDefault", nullable = false)
    private boolean isDefault;

    // Constructors
    public Address() {}

    public Address(Ward ward, String streetAddress, String postalCode, boolean isDefault) {
        this.ward = ward;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.isDefault = isDefault;
    }

    // Getters and Setters
    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressID=" + addressID +
                ", ward=" + ward +
                ", streetAddress='" + streetAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
