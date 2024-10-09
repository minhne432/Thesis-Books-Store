package com.comestic.shop.service;

import com.comestic.shop.model.Ward;
import com.comestic.shop.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WardService {

    @Autowired
    private WardRepository wardRepository;

    // Lấy danh sách tất cả các Ward
    public List<Ward> getAllWards() {
        return wardRepository.findAll();
    }

    // Lấy danh sách các Ward theo DistrictID
    public List<Ward> getWardsByDistrictId(Integer districtID) {
        return wardRepository.findByDistrict_DistrictID(districtID);
    }

    // Lấy thông tin Ward theo ID
    public Optional<Ward> getWardById(int id) {
        return wardRepository.findById(id);
    }

    // Thêm mới hoặc cập nhật Ward
    public Ward saveWard(Ward ward) {
        return wardRepository.save(ward);
    }

    // Xóa Ward theo ID
    public void deleteWard(int id) {
        wardRepository.deleteById(id);
    }
}
