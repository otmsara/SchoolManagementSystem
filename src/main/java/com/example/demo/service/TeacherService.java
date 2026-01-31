package com.example.demo.service;

import java.util.*;

import com.example.demo.model.Course;
import com.example.demo.model.Teacher;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TeacherService {

	private final TeacherRepository teacherRepository;
	private final CourseRepository courseRepository;  // Une seule injection

	@Autowired
	public TeacherService(TeacherRepository teacherRepository,
						  CourseRepository courseRepository) {
		this.teacherRepository = teacherRepository;
		this.courseRepository = courseRepository;
	}

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
			throw new IllegalStateException("teacher with id " + teacherId + " does not exist");
		}
		teacherRepository.deleteById(teacherId);
	}

	@Transactional
	public void updateTeacher(Long teacherId, String name, String email, String faculty, String degree) {
		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new IllegalStateException("teacher with id " + teacherId + " does not exist"));

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

	@Transactional
	public Course assignTeacher(Long courseId, Long teacherId) {
		Course c = courseRepository.findById(courseId)
				.orElseThrow(() -> new IllegalStateException("Course not found"));
		Teacher t = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new IllegalStateException("Teacher not found"));
		c.setTeacher(t);
		return courseRepository.save(c);
	}
}