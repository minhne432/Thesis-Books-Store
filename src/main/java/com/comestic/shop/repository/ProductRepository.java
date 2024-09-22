package com.comestic.shop.repository;

import com.comestic.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Bạn có thể thêm các phương thức tùy chỉnh ở đây, ví dụ:
    // List<Product> findByCategory(String category);
}