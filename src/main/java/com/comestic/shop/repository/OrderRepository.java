package com.comestic.shop.repository;

import com.comestic.shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
    // List<Order> findByCustomerId(int customerId);
}
