package com.comestic.shop.model;

import com.comestic.shop.model.RolePermission;
import com.comestic.shop.model.UserRole;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleID;

    private String roleName;
    private String description;

    @OneToMany(mappedBy = "role")
    private Set<UserRole> userRoles;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)  // Đổi từ LAZY sang EAGER
    private Set<RolePermission> rolePermissions;

    // Getter và Setter
    // ...

    public int getRoleID() {
        return roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public Set<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void setRolePermissions(Set<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
}