package com.comestic.shop.service;

import com.comestic.shop.model.CustomUserDetails;
import com.comestic.shop.model.Customer;
import com.comestic.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional  // Đảm bảo phương thức này nằm trong giao dịch của Hibernate
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);

        if (customerOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        Customer customer = customerOptional.get();
        return new CustomUserDetails(customer);
    }
}
