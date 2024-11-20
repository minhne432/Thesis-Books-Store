package com.comestic.shop.model;

import com.comestic.shop.model.Product;
import com.comestic.shop.model.Order;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ORDER_DETAILS")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailID")
    private Long orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "UnitPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
    @OneToOne(mappedBy = "orderDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    // Constructors
    public OrderDetails() {}

    public OrderDetails(Long orderDetailId, Order order, Product product, Integer quantity, BigDecimal unitPrice, Review review) {
        this.orderDetailId = orderDetailId;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.review = review;
    }

    // Getters and Setters
    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    // You might want to add equals(), hashCode(), and toString() methods here
}