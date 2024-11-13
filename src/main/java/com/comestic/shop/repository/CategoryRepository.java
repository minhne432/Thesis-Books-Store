package com.comestic.shop.repository;

import com.comestic.shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh nếu cần.
    // Ví dụ: Tìm kiếm Category theo tên
    Category findByCategoryName(String categoryName);

    List<Category> findAll();

    boolean existsByCategoryName(String categoryName);

    Optional<Category> findById(Integer categoryID);


}