package com.comestic.shop.service;

import com.comestic.shop.model.Customer;
import com.comestic.shop.model.Address;
import com.comestic.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressService addressService; // Tiêm AddressService để xử lý địa chỉ

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(int id, Customer customerDetails, Address newAddress) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setFirstName(customerDetails.getFirstName());
            customer.setLastName(customerDetails.getLastName());
            customer.setEmail(customerDetails.getEmail());
            customer.setPhone(customerDetails.getPhone());
            customer.setActive(customerDetails.isActive());
            customer.setProfilePicture(customerDetails.getProfilePicture());

            // Nếu cần cập nhật địa chỉ, thực hiện qua AddressService
            if (newAddress != null) {
                newAddress.setCustomer(customer); // Gắn địa chỉ mới cho khách hàng
                addressService.saveAddress(newAddress); // Lưu địa chỉ mới
            }

            return customerRepository.save(customer);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic của bạn
        }
    }

    public void deleteCustomer(int id) {
        // Xóa tất cả các địa chỉ của khách hàng trước khi xóa khách hàng
        List<Address> customerAddresses = addressService.getAddressesByCustomerId(id);
        if (!customerAddresses.isEmpty()) {
            for (Address address : customerAddresses) {
                addressService.deleteAddress(address.getAddressID());
            }
        }

        // Xóa khách hàng
        customerRepository.deleteById(id);
    }
}
