package com.comestic.shop.controller;

import com.comestic.shop.model.Branch;
import com.comestic.shop.model.Order;
import com.comestic.shop.model.OrderStatus;
import com.comestic.shop.service.BranchService;
import com.comestic.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BranchService branchService;

    @GetMapping
    public String listOrders(
            @RequestParam(value = "branchId", required = false) Long branchId,
            @RequestParam(value = "status", required = false) OrderStatus status,
            @RequestParam(value = "orderCode", required = false) String orderCode,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        int pageSize = 10; // Số lượng đơn hàng trên mỗi trang
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<Order> orderPage = orderService.getOrders(branchId, status, orderCode, pageable);

        List<Branch> branches = branchService.getAllBranches();
        model.addAttribute("branches", branches);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("selectedBranchId", branchId);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("orderCode", orderCode);
        model.addAttribute("statuses", OrderStatus.values());

        return "order/list";
    }

    // Các phương thức khác nếu cần
}
