package org.project.ecommerce.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password, String authority) {
        String insertUserQuery = "INSERT INTO users(username,password,enabled) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertUserQuery, username, passwordEncoder.encode(password), 1);
        String insertAuthorityQuery = "INSERT INTO authorities (username, authority) VALUES (?, ?)";
        jdbcTemplate.update(insertAuthorityQuery, username, authority);
    }
}
