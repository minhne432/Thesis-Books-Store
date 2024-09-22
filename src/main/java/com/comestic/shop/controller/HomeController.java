package com.comestic.shop.controller.home;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home/home"; // Trả về home.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về login.html
    }

    @GetMapping("/user")
    public String userPage() {
        return "user"; // Trả về user.html
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // Trả về admin.html
    }

    @GetMapping("/default")
    public String defaultPage() {
        return "default"; // Trang mặc định nếu không có vai trò cụ thể
    }
}
