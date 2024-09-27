package com.comestic.shop.service;

import com.comestic.shop.model.District;
import com.comestic.shop.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    // Lấy danh sách tất cả các District
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    // Lấy danh sách các District theo ProvinceID
    public List<District> getDistrictsByProvinceId(int provinceID) {
        return districtRepository.findByProvince_ProvinceID(provinceID);
    }

    // Lấy thông tin District theo ID
    public Optional<District> getDistrictById(int id) {
        return districtRepository.findById(id);
    }

    // Thêm mới hoặc cập nhật District
    public District saveDistrict(District district) {
        return districtRepository.save(district);
    }

    // Xóa District theo ID
    public void deleteDistrict(int id) {
        districtRepository.deleteById(id);
    }
}
