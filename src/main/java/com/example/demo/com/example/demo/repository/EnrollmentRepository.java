package com.example.demo.com.example.demo.repository;

import com.example.demo.com.example.demo.model.Course;
import com.example.demo.com.example.demo.model.Enrollment;
import com.example.demo.com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long id);
    List<Enrollment> findByCourseId(Long id);

    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);}
