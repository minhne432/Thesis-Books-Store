package com.comestic.shop.repository;

import com.comestic.shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần
}
