package com.comestic.shop.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOGIN_HISTORY")
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LoginID")
    private Long loginId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @Column(name = "LoginTimestamp", nullable = false)
    private LocalDateTime loginTimestamp;

    @Column(name = "IPAddress")
    private String ipAddress;

    @Column(name = "DeviceInfo")
    private String deviceInfo;

    @Column(name = "LoginStatus", nullable = false)
    private String loginStatus;

    // Constructors
    public LoginHistory() {}

    public LoginHistory(Customer customer, LocalDateTime loginTimestamp, String ipAddress, String deviceInfo, String loginStatus) {
        this.customer = customer;
        this.loginTimestamp = loginTimestamp;
        this.ipAddress = ipAddress;
        this.deviceInfo = deviceInfo;
        this.loginStatus = loginStatus;
    }

    // Getters and Setters
    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getLoginTimestamp() {
        return loginTimestamp;
    }

    public void setLoginTimestamp(LocalDateTime loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    // You might want to add equals(), hashCode(), and toString() methods here
}