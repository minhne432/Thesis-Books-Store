package com.comestic.shop.model;

import com.comestic.shop.model.PurchaseOrderDetails;
import com.comestic.shop.model.CartItem;
import com.comestic.shop.model.Review;
import com.comestic.shop.model.OrderDetails;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productID;

    private String productName;
    private String brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryID", nullable = false)
    private Category category;  // Liên kết với Category entity

    private String description;
    private BigDecimal price;
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

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrand() {
        return brand;
    }


    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public Set<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Set<PurchaseOrderDetails> getPurchaseOrderDetails() {
        return purchaseOrderDetails;
    }

    public Set<Inventory> getInventories() {
        return inventories;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setOrderDetails(Set<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void setPurchaseOrderDetails(Set<PurchaseOrderDetails> purchaseOrderDetails) {
        this.purchaseOrderDetails = purchaseOrderDetails;
    }

    public void setInventories(Set<Inventory> inventories) {
        this.inventories = inventories;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
