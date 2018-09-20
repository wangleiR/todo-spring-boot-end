package com.thoughtworks.restfulAPI.restfulAPI.controller;

import com.thoughtworks.restfulAPI.restfulAPI.model.User;
import com.thoughtworks.restfulAPI.restfulAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void register(@RequestBody User user){
        userService.register(user);
    }
}
