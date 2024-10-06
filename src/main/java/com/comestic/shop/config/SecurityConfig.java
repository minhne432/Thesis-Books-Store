package com.comestic.shop.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Session-based security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll() // Use requestMatchers
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // Restrict access based on roles
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/products", true) // Redirect to /home on successful login
                        .failureUrl("/login?error=true") // Redirect to /login on failure
                )
//                .sessionManagement((session) -> session
//                        .invalidSessionUrl("/login?session=invalid") // Handle invalid sessions
//                )
                .logout((logout) -> logout
                                .permitAll()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true") // Redirect on logout
                );
        return http.build();
    }
}
