package com.comestic.shop.model;

import com.comestic.shop.model.Customer;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartID;

    @OneToOne
    @JoinColumn(name = "customerID")
    private Customer customer;

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;

    // Getter và Setter
    // ...

    public int getCartID() {
        return cartID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}