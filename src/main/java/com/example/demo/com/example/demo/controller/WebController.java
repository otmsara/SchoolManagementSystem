package com.example.demo.com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "redirect:/admin/dashboard";  // Redirection temporaire
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // =============== ADMIN ===============
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/students")
    public String adminStudents() {
        return "admin/students";
    }

    @GetMapping("/admin/teachers")
    public String adminTeachers() {
        return "admin/teachers";
    }

    @GetMapping("/admin/courses")
    public String adminCourses() {
        return "admin/courses";
    }

    @GetMapping("/admin/enrollments")
    public String adminEnrollments() {
        return "admin/enrollments";
    }

    // =============== TEACHER ===============
    @GetMapping("/teacher/dashboard")
    public String teacherDashboard() {
        return "teacher/dashboard";
    }

    @GetMapping("/teacher/my-courses")
    public String teacherMyCourses() {
        return "teacher/my-courses";
    }

    @GetMapping("/teacher/students")
    public String teacherStudents() {
        return "teacher/students";
    }

    // =============== STUDENT ===============
    @GetMapping("/student/dashboard")
    public String studentDashboard() {
        return "student/dashboard";
    }

    @GetMapping("/student/my-courses")
    public String studentMyCourses() {
        return "student/my-courses";
    }

    @GetMapping("/student/enroll")
    public String studentEnroll() {
        return "student/enroll";
    }
}
