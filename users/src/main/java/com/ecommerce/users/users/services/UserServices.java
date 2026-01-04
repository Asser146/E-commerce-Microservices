package com.ecommerce.users.users.services;


import com.ecommerce.users.users.models.User;
import com.ecommerce.users.users.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServices {

    private final UserRepository userRepository;
//    private final RestTemplate restTemplate;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.restTemplate = new RestTemplate();
    }

//    public void fetchAndSaveUsers() {
//        String url = "https://api.escuelajs.co/api/v1/users";
//
//        // Fetch the array of users
//        User[] users = restTemplate.getForObject(url, User[].class);
//        if (users != null) {
//            // Save all users to DB
//            userRepository.saveAll(Arrays.asList(users));
//        }
//    }
        public List<User> getUsers() {

            return userRepository.findAll();

    }
}
