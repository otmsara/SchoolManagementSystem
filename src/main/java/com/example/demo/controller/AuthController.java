package com.example.demo.controller;

import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.security.model.AuthRequest;
import com.example.demo.security.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          UserRepository userRepo) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AuthRequest req) {
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

        // RETOUR AMÉLIORÉ : Token + Rôle pour redirection côté client
        return Map.of(
                "token", token,
                "role", user.getRole().name(),
                "username", user.getUsername()
        );
    }

    // SUPPRESSION TOTALE DE LA MÉTHODE REGISTER
}