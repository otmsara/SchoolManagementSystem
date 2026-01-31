package com.example.demo.controller;

import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.security.model.AuthRequest;
import com.example.demo.security.model.RegisterRequest;
import com.example.demo.security.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")  // ✅ CORRECTION: Ajout du préfixe /api
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          UserRepository userRepo,
                          PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest req) {
        // Authenticate user
        authManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );

        // Retrieve user from database
        User user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT token with role
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        return Map.of("token", token);
    }


    @PostMapping("/register")
    public Map<String, String> register(@RequestBody RegisterRequest req) {
        // Create user
        var user = new com.example.demo.security.model.User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(com.example.demo.security.model.Role.STUDENT);

        userRepo.save(user);

        // Return success message
        return Map.of("message", "User registered successfully");
    }

}