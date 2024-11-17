package com.comestic.shop.controller;

import com.comestic.shop.model.Order;
import com.comestic.shop.model.OrderStatus;
import com.comestic.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listOrders(
            @RequestParam(value = "branchId", required = false) Long branchId,
            @RequestParam(value = "status", required = false) OrderStatus status,
            @RequestParam(value = "orderCode", required = false) String orderCode,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        // Lấy danh sách permissions của người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> permissions = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Thêm permissions vào model để hiển thị trong view
        model.addAttribute("permissions", permissions);

        int pageSize = 10; // Số lượng đơn hàng trên mỗi trang
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<Order> orderPage = orderService.getOrders(branchId, status, orderCode, pageable);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("orderPage", orderPage);

        return "order/list";
    }
}
