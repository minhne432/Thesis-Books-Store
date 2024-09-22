package com.comestic.shop.repository;

import com.comestic.shop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần
}
