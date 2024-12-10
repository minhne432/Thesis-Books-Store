package com.comestic.shop.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        // Danh sách các vai trò chuyển hướng đến cùng một trang
        Set<String> branchRoles = Set.of("ROLE_ADMIN", "ROLE_INVENTORY_MANAGER");

        // Kiểm tra nếu user có bất kỳ vai trò nào trong branchRoles
        boolean isBranchRole = authentication.getAuthorities().stream()
                .anyMatch(authority -> branchRoles.contains(authority.getAuthority()));

        if (isBranchRole) {
            response.sendRedirect("/branches");
        } else {
            response.sendRedirect("/shop/products/list");
        }
    }

}
