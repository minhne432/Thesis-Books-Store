package com.comestic.shop.controller;

import com.comestic.shop.model.Customer;
import com.comestic.shop.model.Role;
import com.comestic.shop.model.UserRole;
import com.comestic.shop.service.CustomerService;
import com.comestic.shop.service.RoleService;
import com.comestic.shop.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class CustomerRoleController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * Display the list of all customers (optional).
     */
    @GetMapping("/customers")
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "admin/customer-list"; // Thymeleaf template
    }

    /**
     * Display the roles assigned to a specific customer and all available roles.
     */
    @GetMapping("/customers/{id}/roles")
    public String editCustomerRoles(@PathVariable("id") int customerId, Model model, RedirectAttributes redirectAttributes) {
        // Tìm kiếm khách hàng theo ID
        Optional<Customer> optionalCustomer = customerService.getCustomerById(customerId);

        // Kiểm tra nếu không tìm thấy khách hàng
        if (optionalCustomer.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Khách hàng không tồn tại.");
            return "redirect:/admin/customers";  // Chuyển hướng đến danh sách khách hàng với thông báo lỗi
        }

        Customer customer = optionalCustomer.get();

        // Lấy danh sách tất cả các vai trò có sẵn
        List<Role> allRoles = roleService.getAllRoles();

        if (allRoles.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy vai trò nào.");
            return "redirect:/admin/customers";  // Chuyển hướng với thông báo nếu không tìm thấy vai trò
        }

        // Lấy các vai trò hiện tại đã gán cho khách hàng
        Set<UserRole> userRoles = customer.getUserRoles();
        Set<Integer> assignedRoleIds = new HashSet<>();

        for (UserRole userRole : userRoles) {
            assignedRoleIds.add(userRole.getRole().getRoleID());
        }

        // Đưa thông tin khách hàng, danh sách vai trò và danh sách vai trò đã được gán vào Model
        model.addAttribute("customer", customer);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("assignedRoleIds", assignedRoleIds);

        // Trả về trang Thymeleaf cho việc chỉnh sửa vai trò của khách hàng
        return "admin/customer-roles";
    }


    /**
     * Update the roles assigned to the customer.
     */
    @PostMapping("/customers/{id}/roles")
    public String updateCustomerRoles(
            @PathVariable("id") int customerId,
            @RequestParam(value = "roleIds", required = false) List<Integer> roleIds,
            RedirectAttributes redirectAttributes) {

        // Lấy thông tin khách hàng dựa trên customerId
        Optional<Customer> optionalCustomer = customerService.getCustomerById(customerId);
        if (!optionalCustomer.isPresent()) {
            // Xử lý khi không tìm thấy khách hàng
            redirectAttributes.addFlashAttribute("error", "Customer not found");
            return "redirect:/admin/customers";
        }

        Customer customer = optionalCustomer.get();

        // Nếu không có vai trò nào được chọn, khởi tạo danh sách trống
        if (roleIds == null) {
            roleIds = new ArrayList<>();
        }

        // Lấy danh sách quyền hiện tại của khách hàng
        Set<UserRole> currentUserRoles = customer.getUserRoles();

        // Tạo map để tra cứu nhanh các quyền hiện tại
        Map<Integer, UserRole> currentRoleMap = new HashMap<>();
        for (UserRole userRole : currentUserRoles) {
            currentRoleMap.put(userRole.getRole().getRoleID(), userRole);
        }

        // Duyệt qua danh sách roleIds để thêm quyền mới hoặc giữ lại quyền cũ
        for (Integer roleId : roleIds) {
            if (!currentRoleMap.containsKey(roleId)) {
                // Thêm quyền mới cho khách hàng
                Optional<Role> optionalRole = roleService.getRoleById(roleId);
                if (optionalRole.isPresent()) {
                    UserRole newUserRole = new UserRole();
                    newUserRole.setCustomer(customer);
                    newUserRole.setRole(optionalRole.get());
                    userRoleService.addUserRole(newUserRole);
                } else {
                    // Thông báo nếu roleId không hợp lệ
                    redirectAttributes.addFlashAttribute("error", "Invalid role ID: " + roleId);
                }
            } else {
                // Nếu quyền đã tồn tại, loại bỏ khỏi map để không bị xóa
                currentRoleMap.remove(roleId);
            }
        }

        // Xóa các quyền còn lại trong currentRoleMap vì không còn trong danh sách roleIds
        for (UserRole userRoleToRemove : currentRoleMap.values()) {
            userRoleService.deleteUserRole(userRoleToRemove.getUserRoleID());
        }

        // Thêm thông báo thành công và chuyển hướng sau khi hoàn thành
        redirectAttributes.addFlashAttribute("success", "Roles updated successfully!");
        return "redirect:/admin/customers/" + customerId + "/roles";
    }

}
