package org.project.ecommerce.service;

import jakarta.transaction.Transactional;
import org.project.ecommerce.models.User;
import org.project.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }


    public void saveRoleForUser(User user) {
         user = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRoles(user.getRoles());
        userRepository.save(user);
    }
}

