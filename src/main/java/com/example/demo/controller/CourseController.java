package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ===================== COURSES =====================
    @GetMapping
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
    }

    @PutMapping("/{courseId}/teacher/{teacherId}")
    public Course assignTeacher(@PathVariable Long courseId,
                                @PathVariable Long teacherId) {
        return courseService.assignTeacher(courseId, teacherId);
    }

    // ===================== ENROLLMENTS =====================
    @PostMapping("/enroll/{studentId}/{courseId}")
    public Enrollment enrollStudent(@PathVariable Long studentId,
                                    @PathVariable Long courseId,
                                    @RequestParam(defaultValue = "2025/2026") String academicYear) {
        return courseService.enrollStudent(studentId, courseId, academicYear);
    }

    @GetMapping("/enrollments")
    public List<Enrollment> getEnrollments() {
        return courseService.getEnrollments();
    }
}
