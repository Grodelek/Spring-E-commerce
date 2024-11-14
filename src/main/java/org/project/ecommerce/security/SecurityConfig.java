package org.project.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/products/manage/**").hasRole("ADMIN")
                        .requestMatchers("/products/add", "products/delete/1", "products/update/").hasRole("ADMIN")
                        .requestMatchers("/register").permitAll()
                        .anyRequest().authenticated()
        )

                .formLogin(form ->
                                form.loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                        .defaultSuccessUrl("/", true)
                                .permitAll()
                )
                .logout(logout ->
                                logout
                                        .logoutSuccessUrl("/login")
                                        .permitAll()
                );
    return http.build();
    }
}
