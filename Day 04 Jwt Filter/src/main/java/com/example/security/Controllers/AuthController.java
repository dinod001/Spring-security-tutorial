package com.example.security.Controllers;

import com.example.security.Service.UserService;
import com.example.security.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/save")
    public UserEntity saveUser(@RequestBody UserEntity userEntity) {
        return userService.save(userEntity);
    }
}
