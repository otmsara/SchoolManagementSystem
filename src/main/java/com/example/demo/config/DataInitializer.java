package com.example.demo.config;

import com.example.demo.repository.UserRepository;
import com.example.demo.security.model.Role;
import com.example.demo.security.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        return args -> {

            // ✅ CRÉATION DE L'ADMIN SI N'EXISTE PAS
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("Admin@2025"));
                admin.setEmail("admin@school.com");
                admin.setRole(Role.ADMIN);
                userRepo.save(admin);

                System.out.println("✅ Admin créé : admin / Admin@2025");
            }

            // ✅ EXEMPLE : Ajouter un étudiant de test
            if (userRepo.findByUsername("student1").isEmpty()) {
                User student = new User();
                student.setUsername("student1");
                student.setPassword(passwordEncoder.encode("Student@123"));
                student.setEmail("student1@school.com");
                student.setRole(Role.STUDENT);
                userRepo.save(student);

                System.out.println("✅ Étudiant créé : student1 / Student@123");
            }

            // ✅ EXEMPLE : Ajouter un professeur de test
            if (userRepo.findByUsername("teacher1").isEmpty()) {
                User teacher = new User();
                teacher.setUsername("teacher1");
                teacher.setPassword(passwordEncoder.encode("Teacher@123"));
                teacher.setEmail("teacher1@school.com");
                teacher.setRole(Role.TEACHER);
                userRepo.save(teacher);

                System.out.println("✅ Professeur créé : teacher1 / Teacher@123");
            }
        };
    }
}