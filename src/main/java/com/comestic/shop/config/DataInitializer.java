package com.comestic.shop.config;

import com.comestic.shop.model.Role;
import com.comestic.shop.model.User;
import com.comestic.shop.repository.RoleRepository;
import com.comestic.shop.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public ApplicationRunner initializer(
            UserRepository userRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Tạo các vai trò nếu chưa tồn tại
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByName("ROLE_USER");
            if (userRole == null) {
                userRole = new Role("ROLE_USER");
                roleRepository.save(userRole);
            }

            // Tạo người dùng admin
            if (userRepository.findByUsername("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEnabled(true);
                admin.setRoles(Set.of(adminRole));
                userRepository.save(admin);
            }

            // Tạo người dùng thường
            if (userRepository.findByUsername("user") == null) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setEnabled(true);
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
            }
        };
    }
}