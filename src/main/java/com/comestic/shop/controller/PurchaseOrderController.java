package com.comestic.shop.controller;

import com.comestic.shop.model.PurchaseOrder;
import com.comestic.shop.model.PurchaseOrderDetails;
import com.comestic.shop.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping
    public String listPurchaseOrders(Model model) {
        model.addAttribute("purchaseOrders", purchaseOrderService.getAllPurchaseOrders());
        return "purchase-order/list"; // Giao diện Thymeleaf để hiển thị danh sách đơn đặt hàng
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("purchaseOrder", new PurchaseOrder());
        return "purchase-order/create";
    }

    @PostMapping("/create")
    public String createPurchaseOrder(@ModelAttribute("purchaseOrder") PurchaseOrder purchaseOrder) {
        purchaseOrderService.addPurchaseOrder(purchaseOrder);
        return "redirect:/purchase-orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.getPurchaseOrderById(id);

        if (purchaseOrder.isPresent()) {
            model.addAttribute("purchaseOrder", purchaseOrder.get());
            model.addAttribute("purchaseOrderDetails", purchaseOrder.get().getPurchaseOrderDetails());
            return "purchase-order/edit";
        } else {
            return "redirect:/purchase-orders";
        }
    }




    @PostMapping("/edit/{id}")
    public String updatePurchaseOrder(@PathVariable Long id, @ModelAttribute("purchaseOrder") PurchaseOrder purchaseOrderDetails) {
        purchaseOrderService.updatePurchaseOrder(id, purchaseOrderDetails);
        return "redirect:/purchase-orders";
    }

    @GetMapping("/delete/{id}")
    public String deletePurchaseOrder(@PathVariable Long id) {
        purchaseOrderService.deletePurchaseOrder(id);
        return "redirect:/purchase-orders";
    }


    // Hiển thị chi tiết của một PurchaseOrder
    @GetMapping("{id}/details")
    public String getPurchaseOrderDetails(@PathVariable("id") Long id, Model model) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(id).orElse(null);
        List<PurchaseOrderDetails> purchaseOrderDetails = purchaseOrder.getPurchaseOrderDetails();
        model.addAttribute("purchaseOrder", purchaseOrder);
        model.addAttribute("purchaseOrderDetails", purchaseOrderDetails);
        return "purchase_order_details";  // Tên của file HTML dùng Thymeleaf để hiển thị chi tiết
    }

    // Hiển thị danh sách PurchaseOrder
    @GetMapping("/list")
    public String getAllPurchaseOrders(Model model) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        model.addAttribute("purchaseOrders", purchaseOrders);
        return "purchase_orders";  // Tên của file HTML dùng Thymeleaf để hiển thị danh sách
    }
}
