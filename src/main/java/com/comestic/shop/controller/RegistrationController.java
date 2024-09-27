//package com.comestic.shop.controller;
//
//import org.springframework.ui.Model;
//import com.comestic.shop.model.Customer;
//import com.comestic.shop.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class RegistrationController {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("customer", new Customer());
//        return "register"; // Tên của template Thymeleaf cho trang đăng ký
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("customer") Customer customer) {
//        // Kiểm tra và xử lý đăng ký
//        customerService.registerCustomer(customer);
//        return "redirect:/login";
//    }
//
//
//}