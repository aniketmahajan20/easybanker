package com.example.demo.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.student.models.AuthenticationRequest;
import com.example.demo.student.models.AuthenticationResponse;
import com.example.demo.student.util.JwtUtil;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class StudentController {

    private final StudentService studentService;    

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyStudentDetailsService myStudentDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    // Accessible by Everyone
    @GetMapping("/")
	public String home() {
		return ("<h1>Welcome</h1>");
	}

    @PostMapping("/")
	public String homepost() {
		return ("<h1>Welcome</h1>");
	}

    @GetMapping("/hello") 
    public String hello() {
            return "Hello World";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = myStudentDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
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

