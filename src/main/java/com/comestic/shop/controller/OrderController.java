package com.comestic.shop.controller;

import com.comestic.shop.model.*;
import com.comestic.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    PermissionService permissionService;

    @Autowired
    OrderService orderService;

    @Autowired
    BranchService branchService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderDetailsService orderDetailsService;

    @Autowired
    PermissionCheckerService permissionCheckerService;
    @GetMapping
    public String listOrders(
            @RequestParam(value = "branchId", required = false) Long branchId,
            @RequestParam(value = "status", required = false) OrderStatus status,
            @RequestParam(value = "orderCode", required = false) String orderCode,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        String permissionRequired = "view_orders_list";

        // Kiểm tra permission chung cho danh sách đơn hàng
        if (!permissionCheckerService.hasPermission(permissionRequired)) {
            return "access-denied";
        }

        int pageSize = 5;
        PageRequest pageable = PageRequest.of(page, pageSize);

        // Lấy danh sách đơn hàng từ service, truyền thêm startDate, endDate
        Page<Order> orderPage = orderService.getOrders(branchId, status, orderCode, startDate, endDate, pageable);

        List<Branch> branches = branchService.getAllBranches();
        model.addAttribute("branches", branches);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("selectedBranchId", branchId);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("orderCode", orderCode);
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "order/list";
    }


    @GetMapping("/history")
    public String viewOrderHistory(Model model,
                                   @RequestParam(value = "page", defaultValue = "0") int page) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Retrieve the customer using the username
        Optional<Customer> customerTmp = customerService.getCustomerByUsername(username);
        if (customerTmp.isEmpty()) {
            // Handle the case where the customer is not found
            return "redirect:/login?error";
        }

        Customer customer = customerTmp.get();

        int customerID = customer.getCustomerID();

        // Fetch the orders for the customer
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Order> orderPage = orderService.getOrdersByCustomerID(customerID, pageable);

        // Add attributes to the model
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("orderPage", orderPage);

        return "order/history"; // The view template for displaying order history
    }

    //Customer xem order details
    @GetMapping("/{orderId}/OrderDetails")
    public String viewOrderDetailsCustomer(@PathVariable int orderId, Model model) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Retrieve the customer using the username
        Optional<Customer> customerTpm = customerService.getCustomerByUsername(username);
        if (customerTpm.isEmpty()) {
            // Handle the case where the customer is not found
            return "redirect:/login?error";
        }

        int customerID = customerTpm.get().getCustomerID();

        // Retrieve the order
        Order order = orderService.getOrderById(orderId);
        if (order == null || order.getCustomer().getCustomerID() != customerID) {
            // Order not found or does not belong to the customer
            return "access-denied";
        }

        // Retrieve order details
        List<OrderDetails> orderDetails = orderDetailsService.getOrderDetailsByOrderID(orderId);

        // Add to the model
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);

        return "order/customer-order-details"; // View to display order details
    }


    //Branch manager xem order details
    @GetMapping("/{orderId}/details")
    public String viewOrderDetails(@PathVariable int orderId, Model model) {
        // Retrieve the order
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return "error/404"; // Or appropriate error handling
        }

//        // Permission check
//        Set<String> permissions = permissionService.getUserPermissions();
//        String permissionRequired = "view_orders_branch_" + order.getBranch().getBranchId();
//
//        if (!permissions.contains(permissionRequired)) {
//            return "access-denied";
//        }

        // Retrieve order details
        List<OrderDetails> orderDetails = orderDetailsService.getOrderDetailsByOrderID(orderId);

        // Add to the model
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);

        return "order/details";
    }

    // branch manager cap nhat trang thai don hang
    @GetMapping("/{orderId}/edit")
    public String editOrderStatusForm(@PathVariable int orderId, Model model) {
        // Retrieve the order
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return "error/404";
        }

//        // Permission check
//        Set<String> permissions = permissionService.getUserPermissions();
//        String permissionRequired = "edit_orders_branch_" + order.getBranch().getBranchId();
//
//        if (!permissions.contains(permissionRequired)) {
//            return "access-denied";
//        }

        // Add to the model
        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());

        return "order/edit";
    }

    // branch manager cap nhat trang thai don hang
    @PostMapping("/{orderId}/edit")
    public String updateOrderStatus(@PathVariable int orderId,
                                    @RequestParam("status") OrderStatus status,
                                    Model model) {
        // Retrieve the order
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return "error/404";
        }

//        // Permission check
//        Set<String> permissions = permissionService.getUserPermissions();
//        String permissionRequired = "edit_orders_branch_" + order.getBranch().getBranchId();
//
//        if (!permissions.contains(permissionRequired)) {
//            return "access-denied";
//        }

        // Update the order status using the new method
        orderService.updateOrderStatus(order, status);

        // Redirect to the order list or order details page
        return "redirect:/orders";
    }


}
