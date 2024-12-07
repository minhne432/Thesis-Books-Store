package com.comestic.shop.controller;


import com.comestic.shop.model.Address;
import com.comestic.shop.model.Customer;
import com.comestic.shop.model.CustomerAddress;
import com.comestic.shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CustomerService customerService;

    static class Greeting {
        private String message;
        private String author;

        // Constructors
        public Greeting(String message, String author) {
            this.message = message;
            this.author = author;
        }

        // Getters and Setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }

    @GetMapping("/sayHello")
    @ResponseBody
    public List<Greeting> sayHello(){
        List<Greeting> greetings = new ArrayList<>();
        greetings.add(new Greeting("Hello world!", "Spring Boot"));
        greetings.add(new Greeting("Hi there!", "Java Developer"));
        greetings.add(new Greeting("Greetings!", "Spring Framework"));
        return greetings;
    }

    @RequestMapping("/sayHelloPage")
    public String sayHelloPage(){
        return "test/greetings";
    }


    private Customer getCurrentCustomer() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
    }

    @GetMapping("/testAddress")
    public String listAddress(Model model){
        Customer customers = getCurrentCustomer();
        List<CustomerAddress> customerAddresses = customers.getCustomerAddresses();
        model.addAttribute("customerAddresses",customerAddresses);
        return "test/test_address";
    }
}
