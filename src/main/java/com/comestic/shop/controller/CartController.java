// CartController.java
package com.comestic.shop.controller;

import com.comestic.shop.dto.CartUpdateForm;
import com.comestic.shop.dto.CartUpdateForm.CartItemForm;
import com.comestic.shop.model.CartItem;
import com.comestic.shop.model.Customer;
import com.comestic.shop.service.CartService;
import com.comestic.shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    // Method to get the current authenticated customer
    private Customer getCurrentCustomer() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
    }

    // Display the cart
    @GetMapping
    public String viewCart(Model model) {
        Customer customer = getCurrentCustomer();
        List<CartItem> cartItems = cartService.getCartItems(customer);
        double totalAmount = cartService.calculateTotalAmount(customer);

        // Initialize CartUpdateForm with existing cart items
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        List<CartUpdateForm.CartItemForm> cartItemForms = new ArrayList<>();
        for (CartItem item : cartItems) {
            CartItemForm form = new CartItemForm();
            form.setCartItemId(item.getCartItemID());
            form.setQuantity(item.getQuantity());
            cartItemForms.add(form);
        }
        cartUpdateForm.setCartItems(cartItemForms);

        // Add attributes to the model
        model.addAttribute("cartUpdateForm", cartUpdateForm);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);
        return "cart/cart";
    }

    // Update cart items
    @PostMapping("/update")
    public String updateCartItems(@ModelAttribute CartUpdateForm cartUpdateForm) {
        for (CartUpdateForm.CartItemForm itemForm : cartUpdateForm.getCartItems()) {
            cartService.updateCartItem(itemForm.getCartItemId(), itemForm.getQuantity());
        }
        return "redirect:/cart";
    }

    // Add product to cart
    @PostMapping("/add")
    public String addProductToCart(@RequestParam("productId") int productId,
                                   @RequestParam("quantity") int quantity) {
        Customer customer = getCurrentCustomer();
        cartService.addProductToCart(customer, productId, quantity);
        return "redirect:/cart";
    }

    // Remove cart item
    @GetMapping("/remove/{cartItemId}")
    public String removeCartItem(@PathVariable int cartItemId) {
        cartService.removeCartItem(cartItemId);
        return "redirect:/cart";
    }
}
