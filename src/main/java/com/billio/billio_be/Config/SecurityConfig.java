package com.billio.billio_be.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for API endpoints
                .authorizeHttpRequests(auth -> auth
                        // Define public endpoints
                        .requestMatchers("/api/**").permitAll()  // Allow all API endpoints
                        .requestMatchers("/login").permitAll()   // Allow login endpoint
                        .requestMatchers("/register").permitAll() // Allow registration endpoint
                        // You can add more public endpoints as needed
                        .anyRequest().authenticated()  // Require authentication for any other request
                );

        return http.build();
    }
}