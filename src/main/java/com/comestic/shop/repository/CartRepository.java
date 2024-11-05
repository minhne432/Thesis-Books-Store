package com.comestic.shop.repository;

import com.comestic.shop.model.Cart;
import com.comestic.shop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// CartRepository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByCustomer(Customer customer);
}