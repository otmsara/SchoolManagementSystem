package com.example.demo.com.example.demo.service;

import java.util.*;

import com.example.demo.com.example.demo.model.Course;
import com.example.demo.com.example.demo.model.Enrollment;
import com.example.demo.com.example.demo.model.Student;
import com.example.demo.com.example.demo.repository.CourseRepository;
import com.example.demo.com.example.demo.repository.EnrollmentRepository;
import com.example.demo.com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;


@Service
public class StudentService {

	private final StudentRepository studentRepository;
	@Autowired
	private StudentRepository studentRepo;
	@Autowired
	private CourseRepository courseRepo;
	@Autowired
	private EnrollmentRepository enrollmentRepo;
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@GetMapping
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
			throw new IllegalStateException("student with id " + studentId + "does not exist");
		}
		studentRepository.deleteById(studentId);
	}

	@Transactional // doesn't require JPQL Query
	public void updateStudent(Long studentId, String name, String email, int years, String major) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalStateException("student with id " + studentId + "does not exist"));
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

	public Student getStudents(Long studentId) {
		return studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalStateException("student with id " + studentId + "does not exist"));
	}

	public void enrollStudent(Long studentId, Long courseId) {
		Student s = studentRepo.findById(studentId).orElseThrow();
		Course c = courseRepo.findById(courseId).orElseThrow();

		Enrollment e = new Enrollment();
		e.setStudent(s);
		e.setCourse(c);
		e.setAcademicYear("2025/2026");

		enrollmentRepo.save(e);
	}

}
