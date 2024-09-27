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

    // Lấy danh sách các Address theo CustomerID
    public List<Address> getAddressesByCustomerId(int customerID) {
        return addressRepository.findByCustomer_CustomerID(customerID);
    }

    // Lấy danh sách các Address theo WardID
    public List<Address> getAddressesByWardId(int wardID) {
        return addressRepository.findByWard_WardID(wardID);
    }

    // Lấy thông tin Address theo ID
    public Optional<Address> getAddressById(int id) {
        return addressRepository.findById(id);
    }

    // Thêm mới hoặc cập nhật Address
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    // Xóa Address theo ID
    public void deleteAddress(int id) {
        addressRepository.deleteById(id);
    }
}
