package com.comestic.shop.service;

import com.comestic.shop.model.Cart;
import com.comestic.shop.model.CartItem;
import com.comestic.shop.model.Customer;
import com.comestic.shop.model.Product;
import com.comestic.shop.repository.CartItemRepository;
import com.comestic.shop.repository.CartRepository;
import com.comestic.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    // Lấy giỏ hàng của khách hàng
    public Cart getCartByCustomer(Customer customer) {
        return cartRepository.findByCustomer(customer)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setCustomer(customer);
                    cart.setCreatedDate(new Date());
                    return cartRepository.save(cart);
                });
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addProductToCart(Customer customer, int productId, int quantity) {
        Cart cart = getCartByCustomer(customer);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    // Lấy danh sách các mục trong giỏ hàng
    public List<CartItem> getCartItems(Customer customer) {
        Cart cart = getCartByCustomer(customer);
        return cartItemRepository.findByCart(cart);
    }

    // Các phương thức khác như cập nhật số lượng, xóa sản phẩm khỏi giỏ hàng...
}

