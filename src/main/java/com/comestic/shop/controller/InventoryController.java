package com.comestic.shop.controller;

import com.comestic.shop.model.Inventory;
import com.comestic.shop.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public String listInventories(Model model) {
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "inventory/list"; // Giao diện Thymeleaf để hiển thị danh sách tồn kho
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("inventory", new Inventory());
        return "inventory/create";
    }

    @PostMapping("/create")
    public String createInventory(@ModelAttribute("inventory") Inventory inventory) {
        inventoryService.addInventory(inventory);
        return "redirect:/inventories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Inventory> inventory = inventoryService.getInventoryById(id);
        if (inventory.isPresent()) {
            model.addAttribute("inventory", inventory.get());
            return "inventory/edit";
        } else {
            return "redirect:/inventories";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateInventory(@PathVariable Long id, @ModelAttribute("inventory") Inventory inventoryDetails) {
        inventoryService.updateInventory(id, inventoryDetails);
        return "redirect:/inventories";
    }

    @GetMapping("/delete/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories";
    }
}
