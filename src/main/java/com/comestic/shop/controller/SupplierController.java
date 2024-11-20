package com.comestic.shop.controller;

import com.comestic.shop.model.Supplier;
import com.comestic.shop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public String listSuppliers(
            @RequestParam(value = "page", defaultValue = "0") int page,  // Current page number
            @RequestParam(value = "size", defaultValue = "10") int size, // Page size
            @RequestParam(value = "keyword", required = false) String keyword, // Search keyword
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Supplier> supplierPage;

        if (keyword != null && !keyword.isEmpty()) {
            // Search suppliers by name
            supplierPage = supplierService.searchSuppliersByName(keyword, pageable);
            model.addAttribute("keyword", keyword);
        } else {
            // Get all suppliers
            supplierPage = supplierService.getAllSuppliers(pageable);
        }

        // Add attributes to the model
        model.addAttribute("suppliers", supplierPage.getContent());
        model.addAttribute("supplierPage", supplierPage);

        return "supplier/list";
    }

    // Hiển thị form thêm mới nhà cung cấp
    @GetMapping("/add")
    public String showAddSupplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier/add-supplier";
    }

    // Xử lý lưu nhà cung cấp mới
    @PostMapping("/add")
    public String addSupplier(@ModelAttribute("supplier") Supplier supplier, RedirectAttributes redirectAttributes) {
        supplierService.addSupplier(supplier);
        redirectAttributes.addFlashAttribute("success", "Thêm nhà cung cấp thành công!");
        return "redirect:/suppliers";
    }

    // Hiển thị form chỉnh sửa nhà cung cấp
    @GetMapping("/edit/{id}")
    public String showEditSupplierForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Supplier> optionalSupplier = supplierService.getSupplierById(id);
        if (optionalSupplier.isPresent()) {
            model.addAttribute("supplier", optionalSupplier.get());
            return "supplier/edit";
        } else {
            redirectAttributes.addFlashAttribute("error", "Nhà cung cấp không tồn tại!");
            return "redirect:/suppliers";
        }
    }

    // Xử lý cập nhật nhà cung cấp
    @PostMapping("/edit")
    public String editSupplier(@ModelAttribute("supplier") Supplier supplier, RedirectAttributes redirectAttributes) {
        supplierService.updateSupplier(supplier.getSupplierId(), supplier);
        redirectAttributes.addFlashAttribute("success", "Cập nhật nhà cung cấp thành công!");
        return "redirect:/suppliers";
    }

    // Xóa nhà cung cấp
    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        supplierService.deleteSupplier(id);
        redirectAttributes.addFlashAttribute("success", "Xóa nhà cung cấp thành công!");
        return "redirect:/suppliers";
    }
}
