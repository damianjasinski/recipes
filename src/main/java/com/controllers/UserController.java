package com.controllers;


import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {


    private UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User newUser) {
        userService.checkUser(newUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}