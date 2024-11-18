package com.comestic.shop.controller;

import com.comestic.shop.model.Permission;
import com.comestic.shop.model.Role;
import com.comestic.shop.model.RolePermission;
import com.comestic.shop.service.PermissionService;
import com.comestic.shop.service.RolePermissionService;
import com.comestic.shop.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    // Hiển thị danh sách tất cả các Role
    @GetMapping
    public String listRoles(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "roles/list"; // Trỏ tới template Thymeleaf: roles/list.html
    }

    // Hiển thị form tạo Role mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("role", new Role());
        return "roles/create"; // Trỏ tới template Thymeleaf: roles/create.html
    }

    // Xử lý việc tạo Role mới
    @PostMapping("/create")
    public String createRole(@ModelAttribute("role") Role role) {
        roleService.addRole(role);
        return "redirect:/roles";
    }

    // Hiển thị form chỉnh sửa Role
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Role role = roleService.getRoleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role ID: " + id));
        model.addAttribute("role", role);
        return "roles/edit"; // Trỏ tới template Thymeleaf: roles/edit.html
    }

    // Xử lý việc cập nhật Role
    @PostMapping("/edit/{id}")
    public String updateRole(@PathVariable("id") int id, @ModelAttribute("role") Role roleDetails) {
        roleService.updateRole(id, roleDetails);
        return "redirect:/roles";
    }

    // Xóa Role
    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable("id") int id) {
        roleService.deleteRole(id);
        return "redirect:/roles";
    }

    // Hiển thị form gán Permission cho Role
    @GetMapping("/assign-permissions/{id}")
    public String showAssignPermissionsForm(@PathVariable("id") int roleId, Model model) {
        Role role = roleService.getRoleById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role ID: " + roleId));
        List<Permission> allPermissions = permissionService.getAllPermissions();

        // Lấy danh sách ID của các Permission đã được gán cho Role
        Set<Integer> assignedPermissionIds = new HashSet<>();
        if (role.getRolePermissions() != null) {
            for (RolePermission rp : role.getRolePermissions()) {
                assignedPermissionIds.add(rp.getPermission().getPermissionID());
            }
        }

        model.addAttribute("role", role);
        model.addAttribute("allPermissions", allPermissions);
        model.addAttribute("assignedPermissionIds", assignedPermissionIds);

        return "roles/assign-permissions"; // Trỏ tới template Thymeleaf: roles/assign-permissions.html
    }

    // Xử lý việc gán Permission cho Role
    @PostMapping("/assign-permissions/{id}")
    public String assignPermissionsToRole(@PathVariable("id") int roleId,
                                          @RequestParam(value = "permissionIds", required = false) List<Integer> permissionIds) {

        Role role = roleService.getRoleById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role ID: " + roleId));

        // Xóa tất cả các RolePermission hiện tại của Role này
        Set<RolePermission> currentRolePermissions = role.getRolePermissions();
        if (currentRolePermissions != null) {
            for (RolePermission rp : currentRolePermissions) {
                rolePermissionService.deleteRolePermission(rp.getRolePermissionID());
            }
            role.getRolePermissions().clear();
        }

        // Nếu có Permission được chọn, tạo mới RolePermission
        if (permissionIds != null) {
            for (Integer permissionId : permissionIds) {
                Permission permission = permissionService.getPermissionById(permissionId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid permission ID: " + permissionId));
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRole(role);
                rolePermission.setPermission(permission);
                rolePermissionService.addRolePermission(rolePermission);
            }
        }
        return "redirect:/roles";
    }
}
