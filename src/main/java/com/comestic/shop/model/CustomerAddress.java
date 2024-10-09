package com.comestic.shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CUSTOMER_ADDRESS")
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerAddressID")
    private Long customerAddressID;

    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "AddressID", nullable = false)
    private Address address;

    @Column(name = "IsDefault", nullable = false)
    private boolean isDefault;

    // Constructors
    public CustomerAddress() {}

    public CustomerAddress(Customer customer, Address address, boolean isDefault) {
        this.customer = customer;
        this.address = address;
        this.isDefault = isDefault;
    }

    // Getters and Setters
    public Long getCustomerAddressID() {
        return customerAddressID;
    }

    public void setCustomerAddressID(Long customerAddressID) {
        this.customerAddressID = customerAddressID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "CustomerAddress{" +
                "customerAddressID=" + customerAddressID +
                ", customer=" + customer +
                ", address=" + address +
                ", isDefault=" + isDefault +
                '}';
    }
}
