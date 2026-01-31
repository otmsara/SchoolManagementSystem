package com.example.demo.controller;

import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private CourseRepository courseRepo;
    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @GetMapping
    public Map<String, Long> stats() {
        return Map.of(
                "students", studentRepo.count(),
                "teachers", teacherRepo.count(),
                "courses", courseRepo.count(),
                "enrollments", enrollmentRepo.count()
        );
    }
}