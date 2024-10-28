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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    // GET method to show the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register"; // This should be the name of your Thymeleaf template (register.html)
    }

    // POST method to handle form submission for user registration
    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("customer") @Valid Customer customer,
                                   BindingResult result, Model model) {
        // Validate the form fields
        if (result.hasErrors()) {
            return "register"; // return back to the registration form if errors exist
        }

        // Check if email is already registered
        if (customerService.emailExists(customer.getEmail())) {
            model.addAttribute("emailError", "Email is already registered");
            return "register"; // return back with error
        }

        // Save the new customer
        customerService.saveCustomer(customer);

        // Redirect to success page or login page after successful registration
        return "redirect:/login";
    }

    @GetMapping("/TrangChu")
    public  String trangChu(){
        return "home";
    }

    // Hiển thị danh sách tất cả khách hàng
    @GetMapping("/customers")
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers/list"; // Trỏ tới template Thymeleaf: customers/list.html
    }

    // Hiển thị form gán Roles cho Customer
    @GetMapping("/assign-roles/{id}")
    public String showAssignRolesForm(@PathVariable("id") int customerId, Model model) {
        Customer customer = customerService.getCustomerById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + customerId));
        List<Role> allRoles = roleService.getAllRoles();

        // Lấy danh sách ID của các Roles đã được gán cho Customer
        Set<Integer> assignedRoleIds = new HashSet<>();
        if (customer.getUserRoles() != null) {
            for (UserRole ur : customer.getUserRoles()) {
                assignedRoleIds.add(ur.getRole().getRoleID());
            }
        }

        model.addAttribute("customer", customer);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("assignedRoleIds", assignedRoleIds);

        return "customers/assign-roles"; // Trỏ tới template Thymeleaf: customers/assign-roles.html
    }

    // Xử lý việc gán Roles cho Customer
    @PostMapping("/assign-roles/{id}")
    public String assignRolesToCustomer(@PathVariable("id") int customerId,
                                        @RequestParam(value = "roleIds", required = false) List<Integer> roleIds) {

        Customer customer = customerService.getCustomerById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + customerId));

        // Xóa tất cả các UserRole hiện tại của Customer này
        Set<UserRole> currentUserRoles = customer.getUserRoles();
        if (currentUserRoles != null) {
            for (UserRole ur : currentUserRoles) {
                userRoleService.deleteUserRole(ur.getUserRoleID());
            }
            customer.getUserRoles().clear();
        }

        // Nếu có Role được chọn, tạo mới UserRole
        if (roleIds != null) {
            for (Integer roleId : roleIds) {
                Role role = roleService.getRoleById(roleId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid role ID: " + roleId));
                UserRole userRole = new UserRole();
                userRole.setCustomer(customer);
                userRole.setRole(role);
                userRoleService.addUserRole(userRole);
            }
        }

        return "redirect:/customers";
    }


}
