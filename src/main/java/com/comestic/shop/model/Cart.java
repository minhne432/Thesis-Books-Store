package com.comestic.shop.model;

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
}