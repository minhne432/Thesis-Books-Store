package com.comestic.shop.controller;

import com.comestic.shop.model.Inventory;
import com.comestic.shop.model.Product;
import com.comestic.shop.service.InventoryService;
import com.comestic.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{productId}")
    public String listInventoriesByProduct(@PathVariable("productId") int productId, Model model) {
        Optional<Product> optionalProduct = productService.getProductById(productId);

        if (!optionalProduct.isPresent()) {
            model.addAttribute("errorMessage", "Sản phẩm không tồn tại.");
            return "error";
        }

        Product product = optionalProduct.get();
        List<Inventory> inventories = inventoryService.getInventoriesByProduct(product);

        model.addAttribute("product", product);
        model.addAttribute("inventories", inventories);

        return "product/inventory_by_product";
    }
}
