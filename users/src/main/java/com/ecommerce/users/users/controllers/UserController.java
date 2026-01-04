package com.ecommerce.users.users.controllers;


import com.ecommerce.users.users.models.User;
import com.ecommerce.users.users.services.UserServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserServices userService;

    public UserController(UserServices userService) {
        this.userService = userService;
    }

//    @GetMapping("/fetch-users")
//    public String fetchUsers() {
//        userService.fetchAndSaveUsers();
//        return "Users fetched and saved to database!";
//    }
        @GetMapping("/users")
    public List<User> getUsers() {
        return  userService.getUsers();
    }
}
