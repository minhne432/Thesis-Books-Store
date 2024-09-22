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

    public int getPermissionID() {
        return permissionID;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getDescription() {
        return description;
    }

    public Set<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRolePermissions(Set<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
}
