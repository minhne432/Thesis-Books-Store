package com.comestic.shop.dto;

import com.comestic.shop.model.Address;
import com.comestic.shop.model.Branch;
import com.comestic.shop.model.Customer;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {
    private Customer customer;
    private Address address;
    private Branch branch;
    private double totalAmount;
    private String paymentMethod;
    private List<OrderItemDto> items;

    // Getters và Setters

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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}
