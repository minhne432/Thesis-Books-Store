package com.comestic.shop.controller;

import com.comestic.shop.model.Customer;
import com.comestic.shop.model.OrderDetails;
import com.comestic.shop.model.OrderStatus;
import com.comestic.shop.model.Review;
import com.comestic.shop.service.CustomerService;
import com.comestic.shop.service.OrderDetailsService;
import com.comestic.shop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/new/{orderDetailId}")
    public String showReviewForm(@PathVariable Long orderDetailId, Model model, Principal principal) {
        OrderDetails orderDetails = orderDetailsService.findById(orderDetailId);

        // Check if the order detail belongs to the logged-in customer
        if (!orderDetails.getOrder().getCustomer().getUsername().equals(principal.getName())) {
            return "error/403";
        }

        // Check if a review already exists
        if (orderDetails.getReview() != null || orderDetails.getOrder().getStatus()!= OrderStatus.DELIVERED) {
            return "redirect:/orders/" + orderDetails.getOrder().getOrderID()+"/OrderDetails";
        }

        Review review = new Review();
        model.addAttribute("review", review);
        model.addAttribute("product", orderDetails.getProduct());
        model.addAttribute("orderDetailId", orderDetailId);
        return "review/review-form";
    }

    @PostMapping("/save")
    public String saveReview(@RequestParam Long orderDetailId, @ModelAttribute Review review, Principal principal) {
        OrderDetails orderDetails = orderDetailsService.findById(orderDetailId);

        // Security checks
        if (!orderDetails.getOrder().getCustomer().getUsername().equals(principal.getName())) {
            return "error/403";
        }

        // Set the orderDetails and customer
        review.setOrderDetails(orderDetails);

        // Retrieve the Customer entity
        Optional<Customer> customerTmp = customerService.getCustomerByUsername(principal.getName());

        Customer customer = customerTmp.get();

        review.setCustomer(customer);

        review.setReviewDate(LocalDate.now());

        // Save the review
        reviewService.save(review);

        return "redirect:/orders/" + orderDetails.getOrder().getOrderID() + "/OrderDetails";
    }
}
