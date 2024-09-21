package com.comestic.shop.model;

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
}