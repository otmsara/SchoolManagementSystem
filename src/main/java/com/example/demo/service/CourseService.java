package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



@Service
public class CourseService {

    private final CourseRepository courseRepo;
    private final TeacherRepository teacherRepo;
    private final StudentRepository studentRepo;
    private final EnrollmentRepository enrollmentRepo;

    @Autowired
    public CourseService(CourseRepository courseRepo,
                         TeacherRepository teacherRepo,
                         StudentRepository studentRepo,
                         EnrollmentRepository enrollmentRepo) {
        this.courseRepo = courseRepo;
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    // ===================== COURSES =====================
    public List<Course> getCourses() {
        return courseRepo.findAll();
    }

    public Course createCourse(Course course) {
        return courseRepo.save(course);
    }

    public void deleteCourse(Long courseId) {
        if (!courseRepo.existsById(courseId)) {
            throw new IllegalStateException("Course with id " + courseId + " does not exist");
        }
        courseRepo.deleteById(courseId);
    }

    @Transactional
    public Course assignTeacher(Long courseId, Long teacherId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " does not exist"));
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException("Teacher with id " + teacherId + " does not exist"));
        course.setTeacher(teacher);
        return courseRepo.save(course);
    }

    // ===================== ENROLLMENTS =====================
    @Transactional
    public Enrollment enrollStudent(Long studentId, Long courseId, String academicYear) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exist"));
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " does not exist"));

        // Vérifie si l'étudiant est déjà inscrit
        Optional<Enrollment> existing = enrollmentRepo.findByStudentAndCourse(student, course);
        if (existing.isPresent()) {
            throw new IllegalStateException("Student already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setAcademicYear(academicYear);
        return enrollmentRepo.save(enrollment);
    }

    public List<Enrollment> getEnrollments() {
        return enrollmentRepo.findAll();
    }
}
