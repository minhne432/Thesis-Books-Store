package com.comestic.shop.service;

import com.comestic.shop.model.Province;
import com.comestic.shop.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    // Lấy danh sách tất cả các Province
    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

    // Lấy thông tin Province theo ID
    public Optional<Province> getProvinceById(int id) {
        return provinceRepository.findById(id);
    }

    // Thêm mới hoặc cập nhật Province
    public Province saveProvince(Province province) {
        return provinceRepository.save(province);
    }

    // Xóa Province theo ID
    public void deleteProvince(int id) {
        provinceRepository.deleteById(id);
    }
}
