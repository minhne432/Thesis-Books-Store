package com.comestic.shop.repository;

import com.comestic.shop.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    // Phương thức tìm tất cả các District theo ProvinceID
    List<District> findByProvince_ProvinceID(int provinceID);
}
