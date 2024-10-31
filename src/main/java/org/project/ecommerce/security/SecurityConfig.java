package org.project.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails customer = User.builder()
                .username("customer")
                .password("{noop}test123")
                .roles("CUSTOMER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}root")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(customer, admin);
    }
}
