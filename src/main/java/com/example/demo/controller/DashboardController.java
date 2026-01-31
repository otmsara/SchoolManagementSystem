package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    // STUDENT
    @GetMapping("/student/dashboard")
    public String studentDashboard() {
        return "student/dashboard";
    }

    @GetMapping("/student/enroll")
    public String studentEnroll() {
        return "student/enroll";
    }

    @GetMapping("/student/my-courses")
    public String studentMyCourses() {
        return "student/my-courses";
    }

    // TEACHER
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

    // ADMIN
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
}