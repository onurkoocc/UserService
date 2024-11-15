package com.qrmenu.UserService.domain.controller;


import com.qrmenu.UserService.config.security.JwtTokenProvider;
import com.qrmenu.UserService.domain.model.dto.UserIdentity;
import com.qrmenu.UserService.domain.model.entity.User;
import com.qrmenu.UserService.domain.model.request.LoginRequest;
import com.qrmenu.UserService.domain.model.request.UserRegistrationRequest;
import com.qrmenu.UserService.domain.model.response.LoginResponse;
import com.qrmenu.UserService.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService,
                          JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = userService.loginUser(request);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/validateToken")
    public ResponseEntity<UserIdentity> validateToken(@RequestHeader("Authorization") String token) {
        return userService.validateToken(token);
    }
}
