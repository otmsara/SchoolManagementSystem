package com.example.demo.com.example.demo.service;

import java.util.*;

import com.example.demo.com.example.demo.model.Course;
import com.example.demo.com.example.demo.model.Teacher;
import com.example.demo.com.example.demo.repository.CourseRepository;
import com.example.demo.com.example.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class TeacherService {

	private final TeacherRepository teacherRepository;

	@Autowired
	public TeacherService(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}

	@Autowired
	private TeacherRepository teacherRepo;
	@Autowired
	private CourseRepository courseRepo;
	@GetMapping
	public List<Teacher> getTeachers() {
		return teacherRepository.findAll();
	}

	public void addNewTeacher(Teacher teacher) {
		Optional<Teacher> teacherOptional = teacherRepository.findTeacherByEmail(teacher.getEmail());
		if (teacherOptional.isPresent()) {
			throw new IllegalStateException("email taken");
		}
		teacherRepository.save(teacher);
	}

	public void deleteTeacher(Long teacherId) {
		boolean exists = teacherRepository.existsById(teacherId);
		if (!exists) {
			throw new IllegalStateException("teacher with id " + teacherId + "does not exist");
		}
		teacherRepository.deleteById(teacherId);
	}

	@Transactional // doesn't require JPQL Query
	public void updateTeacher(Long teacherId, String name, String email, String faculty, String degree) {
		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new IllegalStateException("teacher with id " + teacherId + "does not exist"));
		if (name != null && name.length() > 0 && !Objects.equals(teacher.getName(), name)) {
			teacher.setName(name);
		}
		if (email != null && email.length() > 0 && !Objects.equals(teacher.getEmail(), email)) {
			Optional<Teacher> teacherOptional = teacherRepository.findTeacherByEmail(email);
			if (teacherOptional.isPresent()) {
				throw new IllegalStateException("email taken");
			}
			teacher.setEmail(email);
		}
		teacher.setFaculty(faculty);
		teacher.setDegree(degree);
	}

	public Course assignTeacher(Long courseId, Long teacherId) {
		Course c = courseRepo.findById(courseId).orElseThrow();
		Teacher t = teacherRepo.findById(teacherId).orElseThrow();
		c.setTeacher(t);
		return courseRepo.save(c);
	}

}
