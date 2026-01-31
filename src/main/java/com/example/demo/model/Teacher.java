package com.example.demo.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table
@JsonIgnoreProperties({"courses"})  // Évite la récursion infinie
public class Teacher {

    @Id
    @SequenceGenerator(name = "teacher_sequence", sequenceName = "teacher_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_sequence")
    private Long id;

    private String name;
    private String email;
    private LocalDate dob;
    private String faculty;
    private String degree;

    @Transient
    private Integer age;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Course> courses;

    // Constructeurs
    public Teacher() {
    }

    public Teacher(Long id, String name, String email, LocalDate dob, String faculty, String degree) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.faculty = faculty;
        this.degree = degree;
    }

    public Teacher(String name, String email, LocalDate dob, String degree, String faculty) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.faculty = faculty;
        this.degree = degree;
    }

    // Getters et Setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getDegree() {
        return this.degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFaculty() {
        return this.faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    // CORRECTION PRINCIPALE : Vérification de null
    public Integer getAge() {
        if (this.dob == null) {
            return null;  // ou return 0; si vous préférez
        }
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", faculty='" + faculty + '\'' +
                ", degree='" + degree + '\'' +
                '}';
    }
}