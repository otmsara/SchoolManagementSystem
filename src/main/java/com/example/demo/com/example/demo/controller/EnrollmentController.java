package com.example.demo.com.example.demo.controller;

import com.example.demo.com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/enroll/{studentId}/{courseId}")
    public String enroll(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        studentService.enrollStudent(studentId, courseId);
        return "Student enrolled successfully";
    }
}
