package com.comestic.shop.model;

import jakarta.persistence.*;

@Entity
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rolePermissionID;

    @ManyToOne
    @JoinColumn(name = "roleID")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "permissionID")
    private Permission permission;

    // Getter và Setter
    // ...
}
