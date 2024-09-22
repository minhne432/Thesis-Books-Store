package com.comestic.shop.model;

import com.comestic.shop.model.Order;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private Long paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID", nullable = false, unique = true)
    private Order order;

    @Column(name = "PaymentDate", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "AmountPaid", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountPaid;

    @Column(name = "PaymentStatus", nullable = false)
    private String paymentStatus;

    // Constructors
    public Payment() {}

    public Payment(Order order, LocalDate paymentDate, BigDecimal amountPaid, String paymentStatus) {
        this.order = order;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Helper methods
    // Helper methods
    public boolean isFullyPaid() {
        BigDecimal totalAmount = BigDecimal.valueOf(this.order.getTotalAmount());
        return this.amountPaid.compareTo(totalAmount) >= 0;
    }

    public BigDecimal getRemainingAmount() {
        BigDecimal totalAmount = BigDecimal.valueOf(this.order.getTotalAmount());
        return totalAmount.subtract(this.amountPaid);
    }

    public void updatePaymentStatus(String newStatus) {
        this.paymentStatus = newStatus;
        if ("COMPLETED".equals(newStatus)) {
            this.paymentDate = LocalDate.now();
        }
    }

    // You might want to add equals(), hashCode(), and toString() methods here
}
