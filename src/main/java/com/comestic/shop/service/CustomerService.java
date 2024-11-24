package com.comestic.shop.service;

import com.comestic.shop.model.Customer;
import com.comestic.shop.model.CustomerAddress;
import com.comestic.shop.model.Address;
import com.comestic.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerAddressService customerAddressService; // Tiêm CustomerAddressService để xử lý địa chỉ

    // Lưu khách hàng với mật khẩu được mã hóa
    public void saveCustomer(Customer customer) {
        String rawPassword = customer.getPasswordHash();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        customer.setPasswordHash(encodedPassword);
        customerRepository.save(customer);
    }

    // Kiểm tra xem email đã tồn tại chưa
    public boolean emailExists(String email) {
        return customerRepository.findByEmail(email).isPresent();
    }

    // Lấy tất cả khách hàng
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Lấy khách hàng theo ID
    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById(id);
    }

    // Lấy khách hàng theo email
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Optional<Customer> getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    // Thêm khách hàng mới
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Cập nhật thông tin khách hàng và địa chỉ của họ
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

            // Nếu cần cập nhật địa chỉ, thực hiện qua CustomerAddressService
            if (newAddress != null) {
                CustomerAddress customerAddress = new CustomerAddress(customer, newAddress, true);
                customerAddressService.addCustomerAddress(customerAddress); // Lưu địa chỉ mới
            }

            return customerRepository.save(customer);
        } else {
            return null; // Hoặc ném ra ngoại lệ tùy theo logic của bạn
        }
    }

    // Xóa khách hàng và tất cả các địa chỉ của họ
    public void deleteCustomer(int id) {
        // Chuyển đổi từ int sang Long nếu cần
        List<CustomerAddress> customerAddresses = customerAddressService.getAddressesByCustomerId(Long.valueOf(id));

        if (!customerAddresses.isEmpty()) {
            for (CustomerAddress customerAddress : customerAddresses) {
                customerAddressService.deleteCustomerAddress(customerAddress.getCustomerAddressID());
            }
        }

        // Xóa khách hàng
        customerRepository.deleteById(id);
    }


}
