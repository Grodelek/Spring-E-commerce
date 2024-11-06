package org.project.ecommerce.service;

import org.project.ecommerce.models.User;
import org.project.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    private final UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void saveUser(User user){
        userRepository.save(user);
    }

}
