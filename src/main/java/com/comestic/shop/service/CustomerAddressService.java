package com.comestic.shop.service;

import com.comestic.shop.model.CustomerAddress;
import com.comestic.shop.repository.CustomerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAddressService {

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    public List<CustomerAddress> getAddressesByCustomerId(Long customerId) {
        return customerAddressRepository.findByCustomer_CustomerID(customerId);
    }

    public CustomerAddress addCustomerAddress(CustomerAddress customerAddress) {
        return customerAddressRepository.save(customerAddress);
    }

    public void deleteCustomerAddress(Long id) {
        customerAddressRepository.deleteById(id);
    }
}
