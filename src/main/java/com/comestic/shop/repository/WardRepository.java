package com.comestic.shop.repository;

import com.comestic.shop.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {
    // Phương thức tìm tất cả các Ward theo DistrictID
    List<Ward> findByDistrict_DistrictID(int districtID);
}
