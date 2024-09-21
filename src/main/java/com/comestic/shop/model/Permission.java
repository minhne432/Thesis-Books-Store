package com.comestic.shop.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int permissionID;

    private String permissionName;
    private String description;

    @OneToMany(mappedBy = "permission")
    private Set<RolePermission> rolePermissions;

    // Getter và Setter
    // ...
}
