package com.comestic.shop.controller;

import com.comestic.shop.model.*;
import com.comestic.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String listOrders(
            @RequestParam(value = "branchId", required = false) Long branchId,
            @RequestParam(value = "status", required = false) OrderStatus status,
            @RequestParam(value = "orderCode", required = false) String orderCode,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        // Lấy danh sách permissions của người dùng hiện tại
        Set<String> permissions = permissionService.getUserPermissions();
        String permissionRequired = "";

        // Kiểm tra quyền truy cập vào chi nhánh
        if (branchId != null) {
            permissionRequired = "view_orders_branch_" + branchId;
        } else {
            permissionRequired = "view_orders_branch_";
        }

        // Nếu không có quyền truy cập vào branch, chuyển đến trang từ chối quyền truy cập
        if (!permissions.contains(permissionRequired)) {
            return "access-denied";
        }

        // Nếu người dùng nhập mã đơn hàng, kiểm tra mã đó thuộc chi nhánh nào
        if (orderCode != null && !orderCode.isEmpty()) {
            Order order = orderService.getOrderByOrderCode(orderCode);  // Giả sử có phương thức này trong `orderService`
            if (order != null && order.getBranch() != null) {
                // Kiểm tra người dùng có quyền truy cập vào chi nhánh của đơn hàng không
                if (!permissions.contains("view_orders_branch_" + order.getBranch().getBranchId())) {
                    return "access-denied";
                }
            }
        }

        int pageSize = 10; // Số lượng đơn hàng trên mỗi trang
        PageRequest pageable = PageRequest.of(page, pageSize);

        // Lấy danh sách đơn hàng từ service
        Page<Order> orderPage = orderService.getOrders(branchId, status, orderCode, pageable);

        // Lấy danh sách các chi nhánh để hiển thị trong model
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

    @GetMapping("/{orderId}/details")
    public String viewOrderDetails(@PathVariable int orderId, Model model) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Retrieve the customer using the username
        Optional<Customer> customerTmp = customerService.getCustomerByUsername(username);
        if (customerTmp.isEmpty()) {
            // Handle the case where the customer is not found
            return "redirect:/login?error";
        }

        int customerID = customerTmp.get().getCustomerID();

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

        return "order/details"; // View to display order details
    }

}
