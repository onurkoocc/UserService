package com.qrmenu.UserService.domain.service;


import com.qrmenu.UserService.config.security.JwtTokenProvider;
import com.qrmenu.UserService.domain.model.dto.UserIdentity;
import com.qrmenu.UserService.domain.model.entity.User;
import com.qrmenu.UserService.domain.model.request.LoginRequest;
import com.qrmenu.UserService.domain.model.request.UserRegistrationRequest;
import com.qrmenu.UserService.domain.model.response.LoginResponse;
import com.qrmenu.UserService.domain.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public void registerUser(UserRegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
    }

    public LoginResponse loginUser(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String token = jwtTokenProvider.generateToken(authentication.getName());
            return new LoginResponse(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public ResponseEntity<UserIdentity> validateToken(String token) {
        String jwt = token.substring(7);
        if (!jwtTokenProvider.validateToken(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = jwtTokenProvider.getUsernameFromToken(jwt);
        Optional<User> optionalUser =  userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = optionalUser.get();
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(user.getId());
        userIdentity.setUsername(user.getUsername());
        userIdentity.setRole(user.getRole().name());

        return ResponseEntity.ok(userIdentity);
    }
}
