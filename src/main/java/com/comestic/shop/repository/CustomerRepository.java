package com.comestic.shop.repository;

import com.comestic.shop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
     Optional<Customer> findByEmail(String email);

     Optional<Customer> findByUsername (String username);
}
