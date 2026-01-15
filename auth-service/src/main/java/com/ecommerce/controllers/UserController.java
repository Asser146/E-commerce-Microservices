package com.ecommerce.controllers;


import com.ecommerce.models.User;
import com.ecommerce.services.UserServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserServices userService;

    public UserController(UserServices userService) {
        this.userService = userService;
    }

        @GetMapping("/users")
    public List<User> getUsers() {
        return  userService.getUsers();
    }
}
