package com.comestic.shop.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productID;

    private String productName;
    private String brand;
    private String category;
    private String description;
    private double price;
    private int stockQuantity;

    // Quan hệ với các entity khác
    @OneToMany(mappedBy = "product")
    private Set<OrderDetails> orderDetails;

    @OneToMany(mappedBy = "product")
    private Set<CartItem> cartItems;

    @OneToMany(mappedBy = "product")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "product")
    private Set<PurchaseOrderDetails> purchaseOrderDetails;

    @OneToMany(mappedBy = "product")
    private Set<Inventory> inventories;

    // Getter và Setter
    // ...
}
