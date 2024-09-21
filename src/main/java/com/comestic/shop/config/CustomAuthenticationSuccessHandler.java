//package com.comestic.shop.config;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Collection;
//
//@Component
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//
//        // Lấy các vai trò của người dùng sau khi đăng nhập
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        // Chuyển hướng dựa trên vai trò
//        for (GrantedAuthority grantedAuthority : authorities) {
//            String role = grantedAuthority.getAuthority();
//
//            if (role.equals("ROLE_ADMIN")) {
//                response.sendRedirect("/admin");  // Chuyển hướng đến trang admin
//                return;
//            } else if (role.equals("ROLE_USER")) {
//                response.sendRedirect("/user");  // Chuyển hướng đến trang user
//                return;
//            }
//        }
//
//        // Nếu không có vai trò phù hợp, chuyển hướng đến trang mặc định
//        response.sendRedirect("/default");
//    }
//}
