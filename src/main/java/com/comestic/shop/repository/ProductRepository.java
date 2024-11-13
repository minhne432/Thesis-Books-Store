package com.comestic.shop.repository;

import com.comestic.shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Bạn có thể thêm các phương thức tùy chỉnh ở đây, ví dụ:
    // List<Product> findByCategory(String category);
    List<Product> findByProductNameContainingIgnoreCase(String name);

    // Tìm kiếm theo thương hiệu
    List<Product> findByBrandContainingIgnoreCase(String brand);

    // Tìm kiếm theo danh mục
    // Thay đổi để tìm theo thuộc tính chuỗi trong Category (ví dụ: categoryName)
    List<Product> findByCategory_CategoryNameContainingIgnoreCase(String categoryName);

    // Tìm kiếm kết hợp (tuỳ chọn)
    @Query("SELECT p FROM Product p WHERE lower(p.productName) LIKE lower(concat('%', :keyword, '%')) OR lower(p.brand) LIKE lower(concat('%', :keyword, '%')) OR lower(p.category) LIKE lower(concat('%', :keyword, '%'))")
    List<Product> searchProducts(String keyword);

    Page<Product> findByProductNameContaining(String keyword, Pageable pageable);
}