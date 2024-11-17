package com.comestic.shop.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final Customer customer;

    public CustomUserDetails(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Lấy danh sách roles và permissions của người dùng
        return customer.getUserRoles().stream()
                .flatMap(userRole -> {
                    // Gán role với tiền tố ROLE_
                    Collection<GrantedAuthority> roleAuthorities = customer.getUserRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getRoleName()))
                            .collect(Collectors.toList());

                    // Gán permissions cho mỗi role
                    Collection<GrantedAuthority> permissionAuthorities = userRole.getRole().getRolePermissions().stream()
                            .map(rolePermission -> new SimpleGrantedAuthority(rolePermission.getPermission().getPermissionName()))
                            .collect(Collectors.toList());

                    // Kết hợp cả role và permission authorities
                    roleAuthorities.addAll(permissionAuthorities);
                    return roleAuthorities.stream();
                })
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return customer.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return customer.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return customer.isActive();
    }
}
