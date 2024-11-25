package org.project.ecommerce.service;

import org.project.ecommerce.models.User;
import org.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public void registerUser(String username, String password, List<String> roles) {
        if(roles == null){
            roles = new ArrayList<>();
        }
     User user = new User();
     user.setUsername(username);
     user.setPassword(passwordEncoder.encode(password));
     user.setEnabled(1);
     userRepository.save(user);

     roles.add("ROLE_CUSTOMER");
     user.setRoles(roles);
     userService.saveRoleForUser(user);
    }
}
