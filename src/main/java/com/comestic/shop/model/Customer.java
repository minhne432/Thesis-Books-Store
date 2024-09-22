package com.comestic.shop.model;

import com.comestic.shop.model.LoginHistory;
import com.comestic.shop.model.Order;
import com.comestic.shop.model.UserRole;
import com.comestic.shop.model.Cart;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerID;

    private String customerType;
    private String username;
    private String email;
    private String passwordHash;
    private String salt;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Temporal(TemporalType.DATE)
    private Date dateJoined;
    @Temporal(TemporalType.DATE)
    private Date lastLoginDate;
    private boolean isActive;
    private String profilePicture;
    private String preferredLanguage;
    private String securityQuestion;
    private String securityAnswerHash;

    // Quan hệ với các entity khác
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

    @OneToMany(mappedBy = "customer")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "customer")
    private Set<LoginHistory> loginHistories;

    @OneToMany(mappedBy = "customer")
    private Set<UserRole> userRoles;

    // Getter và Setter
    // ...

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswerHash() {
        return securityAnswerHash;
    }

    public Cart getCart() {
        return cart;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Set<LoginHistory> getLoginHistories() {
        return loginHistories;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityAnswerHash(String securityAnswerHash) {
        this.securityAnswerHash = securityAnswerHash;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void setLoginHistories(Set<LoginHistory> loginHistories) {
        this.loginHistories = loginHistories;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}