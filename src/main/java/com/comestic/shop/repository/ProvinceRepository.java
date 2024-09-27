package com.comestic.shop.repository;

import com.comestic.shop.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh tại đây nếu cần
}
