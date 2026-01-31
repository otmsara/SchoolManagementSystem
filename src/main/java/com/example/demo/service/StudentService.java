package com.example.demo.service;

import java.util.*;

import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;


@Service
public class StudentService {

	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;  // ✅ Une seule injection
	private final EnrollmentRepository enrollmentRepository;  // ✅ Une seule injection

	@Autowired
	public StudentService(StudentRepository studentRepository,
						  CourseRepository courseRepository,
						  EnrollmentRepository enrollmentRepository) {
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.enrollmentRepository = enrollmentRepository;
	}

	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	public void addNewStudent(Student student) {
		Optional<Student> studentOptional = studentRepository
				.findStudentByEmail(student.getEmail());
		if (studentOptional.isPresent()) {
			throw new IllegalStateException("email taken");
		}
		studentRepository.save(student);
	}

	public void deleteStudent(Long studentId) {
		boolean exists = studentRepository.existsById(studentId);
		if (!exists) {
			throw new IllegalStateException("student with id " + studentId + " does not exist");
		}
		studentRepository.deleteById(studentId);
	}

	@Transactional
	public void updateStudent(Long studentId, String name, String email, int years, String major) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalStateException("student with id " + studentId + " does not exist"));

		if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
			student.setName(name);
		}

		if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
			Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
			if (studentOptional.isPresent()) {
				throw new IllegalStateException("email taken");
			}
			student.setEmail(email);
		}

		student.setYears(years);
		student.setMajor(major);
	}

	public Student getStudent(Long studentId) {  // ✅ Renommé pour éviter confusion
		return studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalStateException("student with id " + studentId + " does not exist"));
	}

	@Transactional
	public void enrollStudent(Long studentId, Long courseId) {
		Student s = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalStateException("Student not found"));
		Course c = courseRepository.findById(courseId)
				.orElseThrow(() -> new IllegalStateException("Course not found"));

		Enrollment e = new Enrollment();
		e.setStudent(s);
		e.setCourse(c);
		e.setAcademicYear("2025/2026");

		enrollmentRepository.save(e);
	}
}
