package com.comestic.shop.controller;

import com.comestic.shop.model.Supplier;
import com.comestic.shop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public String listSuppliers(Model model) {
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "supplier/list"; // Giao diện Thymeleaf
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier/create";
    }

    @PostMapping("/create")
    public String createSupplier(@ModelAttribute("supplier") Supplier supplier) {
        supplierService.addSupplier(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Supplier> supplier = supplierService.getSupplierById(id);
        if (supplier.isPresent()) {
            model.addAttribute("supplier", supplier.get());
            return "supplier/edit";
        } else {
            return "redirect:/suppliers";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateSupplier(@PathVariable Long id, @ModelAttribute("supplier") Supplier supplierDetails) {
        supplierService.updateSupplier(id, supplierDetails);
        System.out.println("HELLO");
        return "redirect:/suppliers";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return "redirect:/suppliers";
    }
}
