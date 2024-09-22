package com.comestic.shop.repository;

import com.comestic.shop.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần
}