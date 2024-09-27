package com.comestic.shop.service;

import com.comestic.shop.model.CustomUserDetails;
import com.comestic.shop.model.Customer;
import com.comestic.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject BCryptPasswordEncoder

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);

        if (customerOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        Customer customer = customerOptional.get();
        System.out.println("customer ne: "+customer.getPasswordHash());
        // The password comparison happens during authentication, Spring handles it.
        return new CustomUserDetails(customer);
    }


}


