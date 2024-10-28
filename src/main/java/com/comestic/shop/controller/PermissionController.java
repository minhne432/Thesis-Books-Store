package com.comestic.shop.controller;

import com.comestic.shop.model.Permission;
import com.comestic.shop.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // Hiển thị danh sách tất cả các Permission
    @GetMapping
    public String listPermissions(Model model) {
        List<Permission> permissions = permissionService.getAllPermissions();
        model.addAttribute("permissions", permissions);
        return "permissions/list"; // Trỏ tới template Thymeleaf: permissions/list.html
    }

    // Hiển thị form tạo Permission mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("permission", new Permission());
        return "permissions/create"; // Trỏ tới template Thymeleaf: permissions/create.html
    }

    // Xử lý việc tạo Permission mới
    @PostMapping("/create")
    public String createPermission(@ModelAttribute("permission") Permission permission) {
        permissionService.addPermission(permission);
        return "redirect:/permissions";
    }

    // Hiển thị form chỉnh sửa Permission
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Permission permission = permissionService.getPermissionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid permission ID: " + id));
        model.addAttribute("permission", permission);
        return "permissions/edit"; // Trỏ tới template Thymeleaf: permissions/edit.html
    }

    // Xử lý việc cập nhật Permission
    @PostMapping("/edit/{id}")
    public String updatePermission(@PathVariable("id") int id, @ModelAttribute("permission") Permission permissionDetails) {
        permissionService.updatePermission(id, permissionDetails);
        return "redirect:/permissions";
    }

    // Xóa Permission
    @GetMapping("/delete/{id}")
    public String deletePermission(@PathVariable("id") int id) {
        permissionService.deletePermission(id);
        return "redirect:/permissions";
    }
}
