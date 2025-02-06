package com.example.demo.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.student.models.MyStudent;


public interface MyStudentRepository extends JpaRepository<MyStudent, Integer> {
    Optional<MyStudent> findByStudentName(String studentName);
}
