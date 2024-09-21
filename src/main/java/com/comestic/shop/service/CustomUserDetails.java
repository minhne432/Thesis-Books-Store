//package com.comestic.shop.service;
//
//import com.comestic.shop.model.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//public class CustomUserDetails implements UserDetails {
//
//    private User user;
//
//    public CustomUserDetails(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return user.getRoles()
//                .stream()
//                .map(role -> (GrantedAuthority) role::getName)
//                .collect(Collectors.toSet());
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    // Các phương thức khác như isAccountNonExpired, v.v.
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    // ... các phương thức override khác ...
//}