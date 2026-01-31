package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.demo.security.model.Role;
import com.example.demo.security.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/admin/users")
public class UserManagementController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserManagementController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // ENDPOINT POUR CRÉER UN UTILISATEUR (ADMIN UNIQUEMENT)
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody CreateUserRequest request) {

        // Vérifier si l'utilisateur existe déjà
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Username already exists")
            );
        }

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Email already exists")
            );
        }

        // Générer un mot de passe temporaire
        String tempPassword = generateTempPassword();

        // Créer l'utilisateur
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(tempPassword));
        user.setRole(request.getRole());

        userRepo.save(user);

        // Retourner les credentials à envoyer par email
        return ResponseEntity.ok(Map.of(
                "message", "User created successfully",
                "username", user.getUsername(),
                "email", user.getEmail(),
                "tempPassword", tempPassword,
                "role", user.getRole().name()
        ));
    }

    private String generateTempPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    // DTO pour la requête
    public static class CreateUserRequest {
        private String username;
        private String email;
        private Role role;

        // Getters et Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public Role getRole() { return role; }
        public void setRole(Role role) { this.role = role; }
    }
}