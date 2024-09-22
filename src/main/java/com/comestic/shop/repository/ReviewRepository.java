package com.comestic.shop.repository;

import com.comestic.shop.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
     List<Review> findByProductId(Long productId);
     List<Review> findByCustomerId(Long customerId);
}
