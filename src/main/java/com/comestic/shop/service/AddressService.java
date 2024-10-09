package com.comestic.shop.service;

import com.comestic.shop.model.Address;
import com.comestic.shop.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    // Lấy danh sách tất cả các Address
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    // Lấy danh sách các Address theo WardID
    public List<Address> getAddressesByWardId(int wardID) {
        return addressRepository.findByWard_WardID(wardID);
    }

    // Lấy thông tin Address theo ID (chuyển đổi kiểu dữ liệu từ int sang Long)
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    // Thêm mới hoặc cập nhật Address
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    // Xóa Address theo ID (chuyển đổi kiểu dữ liệu từ int sang Long)
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
