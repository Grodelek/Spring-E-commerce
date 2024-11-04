package org.project.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
                .roles("ADMIN","CUSTOMER")
                .build();
        return new InMemoryUserDetailsManager(customer, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
                configurer
                        .anyRequest().authenticated()
        )
                .formLogin(form ->
                                form.loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                        .defaultSuccessUrl("/", true)
                                .permitAll()
                )
                .logout(logout ->
                        logout.permitAll()
                );
    return http.build();
    }
}
