package com.ecommerce.services;


import com.ecommerce.models.User;
import com.ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    private final UserRepository userRepository;
//    private final RestTemplate restTemplate;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.restTemplate = new RestTemplate();
    }

        public List<User> getUsers() {

            return userRepository.findAll();

    }
}
