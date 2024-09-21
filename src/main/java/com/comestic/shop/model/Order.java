package com.comestic.shop.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "`Order`") // Sử dụng dấu ` để tránh xung đột từ khóa SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @Temporal(TemporalType.DATE)
    private Date orderDate;
    private double totalAmount;
    private String status;
    private String paymentMethod;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "customerID")
    private Customer customer;


    @ManyToOne
    @JoinColumn(name = "branchID")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "couponID")
    private Coupon coupon;

    // Quan hệ với các entity khác
    @OneToMany(mappedBy = "order")
    private Set<OrderDetails> orderDetails;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    // Getter và Setter
    // ...

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public void setOrderDetails(Set<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public int getOrderID() {
        return orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Branch getBranch() {
        return branch;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public Set<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public Payment getPayment() {
        return payment;
    }
}