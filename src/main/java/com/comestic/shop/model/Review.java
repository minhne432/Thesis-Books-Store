package com.comestic.shop.model;

import com.comestic.shop.model.Product;
import com.comestic.shop.model.Customer;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "REVIEW")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReviewID")
    private Long reviewId;

    // Relationship with OrderDetails
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderDetailID", nullable = false)
    private OrderDetails orderDetails;

    // Add relationship with Customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;
    @Column(name = "Rating", nullable = false)
    private Integer rating;

    @Column(name = "Comment")
    private String comment;

    @Column(name = "ReviewDate", nullable = false)
    private LocalDate reviewDate;

    // Constructors
    public Review() {}

    public Review(OrderDetails orderDetails, Integer rating, String comment, LocalDate reviewDate) {
        this.orderDetails = orderDetails;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }





    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // You might want to add equals(), hashCode(), and toString() methods here
}
