package com.comestic.shop.controller;


import com.comestic.shop.model.Customer;
import com.comestic.shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

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
}
