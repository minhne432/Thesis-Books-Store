package com.comestic.shop.model;

import com.comestic.shop.model.Permission;
import com.comestic.shop.model.Role;
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


    public int getRolePermissionID() {
        return rolePermissionID;
    }

    public Role getRole() {
        return role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setRolePermissionID(int rolePermissionID) {
        this.rolePermissionID = rolePermissionID;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
