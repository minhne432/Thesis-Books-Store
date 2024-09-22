package com.comestic.shop.service;

import com.comestic.shop.model.Cart;
import com.comestic.shop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(int id) {
        return cartRepository.findById(id);
    }

    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart updateCart(int id, Cart cartDetails) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.setCreatedDate(cartDetails.getCreatedDate());
            cart.setCustomer(cartDetails.getCustomer());
            cart.setCartItems(cartDetails.getCartItems());
            return cartRepository.save(cart);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deleteCart(int id) {
        cartRepository.deleteById(id);
    }
}
