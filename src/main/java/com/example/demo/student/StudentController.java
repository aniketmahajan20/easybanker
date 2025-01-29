package com.example.demo.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    private final StudentService studentService;    

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Accessible by Everyone
    @GetMapping("/")
	public String home() {
		return ("<h1>Welcome</h1>");
	}

    // Acessible by students and Teachers
    @GetMapping("/student")
    public String student() {
        return ("<h1>Welcome Student</h1>");
    }

    // Accessible by Teachers
    @GetMapping("/studentList")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    // Accessible by Teachers
    @PostMapping("/studentList")
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    // Accessible by Teachers
    @DeleteMapping(path = "/studentList/{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }
    
    // Accessible by Teachers
    @PutMapping(path = "/studentList/{studentId}")
    public void updateStudent(
        @PathVariable("studentId") Long studentId,
        @Requestparam(required = false) String name,
        @Requestparam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }
}

