package com.comestic.shop.repository;

import com.comestic.shop.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
    // Optional<Payment> findByOrderId(Long orderId);
}
