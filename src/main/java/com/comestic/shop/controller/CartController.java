package com.comestic.shop.controller;

import com.comestic.shop.model.CartItem;
import com.comestic.shop.model.Customer;
import com.comestic.shop.service.CartService;
import com.comestic.shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    // Giả sử bạn có phương thức để lấy thông tin khách hàng hiện tại
    private Customer getCurrentCustomer() {
        // Lấy thông tin khách hàng từ session hoặc bảo mật
        // Ví dụ:
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerService.getCustomerByUsername(username).orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public String addProductToCart(@RequestParam("productId") int productId,
                                   @RequestParam("quantity") int quantity) {
        Customer customer = getCurrentCustomer();
        cartService.addProductToCart(customer, productId, quantity);
        return "redirect:/cart"; // Chuyển hướng đến trang giỏ hàng
    }

    // Hiển thị giỏ hàng
    @GetMapping
    public String viewCart(Model model) {
        Customer customer = getCurrentCustomer();
        List<CartItem> cartItems = cartService.getCartItems(customer);
        model.addAttribute("cartItems", cartItems);
        return "cart/cart"; // Trang Thymeleaf hiển thị giỏ hàng
    }

    // Các phương thức khác như cập nhật số lượng, xóa sản phẩm khỏi giỏ hàng...
}
