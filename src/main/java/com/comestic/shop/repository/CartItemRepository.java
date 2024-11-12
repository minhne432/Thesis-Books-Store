package com.comestic.shop.repository;

import com.comestic.shop.model.Cart;
import com.comestic.shop.model.CartItem;
import com.comestic.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart(Cart cart);
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    void deleteAllByCart(Cart cart);
}
