package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private StudentRepository studentRepository;

    public void registerNewStudent(Student student) {
        // Perform registration logic, such as validation, data manipulation, etc.
        // For simplicity, let's just save the student to the database
        studentRepository.save(student);
    }
}
