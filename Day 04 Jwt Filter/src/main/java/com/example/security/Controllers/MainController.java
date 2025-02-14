package com.example.security.Controllers;


import com.example.security.Service.JwtService;
import com.example.security.config.SecurityConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final JwtService jwtService;

    public MainController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping
    public String home() {
        return "Hello World";
    }
    @PostMapping("/login")
    public String login() {
        return jwtService.generateToken();
    }

    @GetMapping("/username")
    public String username(@RequestHeader String token) {
        return jwtService.getUsername(token);
    }
}
