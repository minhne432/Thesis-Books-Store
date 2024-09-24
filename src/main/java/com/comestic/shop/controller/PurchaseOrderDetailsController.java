package com.comestic.shop.controller;

import com.comestic.shop.model.PurchaseOrder;
import com.comestic.shop.model.PurchaseOrderDetails;
import com.comestic.shop.service.ProductService;
import com.comestic.shop.service.PurchaseOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/purchase-order-details")
public class PurchaseOrderDetailsController {

    @Autowired
    private PurchaseOrderDetailsService purchaseOrderDetailsService;

    @Autowired
    private ProductService productService;

    // Hiển thị tất cả chi tiết đơn hàng
    @GetMapping
    public String getAllPurchaseOrderDetails(Model model) {
        List<PurchaseOrderDetails> orderDetailsList = purchaseOrderDetailsService.getAllPurchaseOrderDetails();
        model.addAttribute("orderDetailsList", orderDetailsList);
        return "purchase-order-detail/list";
    }

    // Hiển thị form để thêm chi tiết đơn hàng
    @GetMapping("/add")
    public String addPurchaseOrderDetailsForm(Model model) {
        model.addAttribute("purchaseOrderDetails", new PurchaseOrderDetails());
        return "purchase-order-detail/add";
    }

    // Xử lý việc thêm chi tiết đơn hàng
    @PostMapping("/add")
    public String addPurchaseOrderDetails(@ModelAttribute("purchaseOrderDetails") PurchaseOrderDetails purchaseOrderDetails) {
        purchaseOrderDetailsService.addPurchaseOrderDetails(purchaseOrderDetails);
        return "redirect:/purchase-order-details";
    }

    // Hiển thị form để sửa chi tiết đơn hàng
    @GetMapping("/edit/{id}")
    public String editPurchaseOrderDetailsForm(@PathVariable Long id, Model model) {
        Optional<PurchaseOrderDetails> orderDetails = purchaseOrderDetailsService.getPurchaseOrderDetailsById(id);
        if (orderDetails.isPresent()) {
            PurchaseOrderDetails details = orderDetails.get();
            PurchaseOrder currentPurchaseOrder = details.getPurchaseOrder();
            model.addAttribute("purchaseOrderDetails", details);
            model.addAttribute("currentPurchaseOrder", currentPurchaseOrder);
            model.addAttribute("products",productService.getAllProducts());// Truyền purchaseOrder hiện tại vào model
            return "purchase-order-detail/edit";
        }
        return "error";
    }





    // Xử lý việc cập nhật chi tiết đơn hàng
    @PostMapping("/edit/{id}")
    public String updatePurchaseOrderDetails(@PathVariable Long id, @ModelAttribute("purchaseOrderDetails") PurchaseOrderDetails purchaseOrderDetails) {
        purchaseOrderDetailsService.updatePurchaseOrderDetails(id   , purchaseOrderDetails);
        return "redirect:/purchase-order-details";
    }

    // Xóa chi tiết đơn hàng
    @GetMapping("/delete/{id}")
    public String deletePurchaseOrderDetails(@PathVariable Long id) {
        purchaseOrderDetailsService.deletePurchaseOrderDetails(id);
        return "redirect:/purchase-order-details";
    }
}
