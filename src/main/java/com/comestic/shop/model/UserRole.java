package com.comestic.shop.model;

import jakarta.persistence.*;

@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userRoleID;

    @ManyToOne
    @JoinColumn(name = "customerID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "roleID")
    private Role role;

    // Getter và Setter
    // ...

    public int getUserRoleID() {
        return userRoleID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Role getRole() {
        return role;
    }

    public void setUserRoleID(int userRoleID) {
        this.userRoleID = userRoleID;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
