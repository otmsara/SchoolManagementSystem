package com.example.demo.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table
@JsonIgnoreProperties({"enrollments"})  // Évite la récursion infinie
public class Student {

    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private Long id;

    private String name;
    private String email;
    private LocalDate dob;

    @Column(name = "major")  // Correction: minuscule pour respecter les conventions
    private String major;

    @Column(name = "years")  // Correction: minuscule
    private Integer years;

    @Transient
    private Integer age;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    // Constructeurs
    public Student() {
    }

    public Student(Long id, String name, String email, LocalDate dob, String major, Integer years) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.major = major;
        this.years = years;
    }

    public Student(String name, String email, LocalDate dob, String major, Integer years) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.major = major;
        this.years = years;
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

    // ✅ CORRECTION PRINCIPALE : Vérification de null
    public Integer getAge() {
        if (this.dob == null) {
            return null;  // ou return 0; si vous préférez
        }
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", major='" + major + '\'' +
                ", years=" + years +
                '}';
    }
}