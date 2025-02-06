package com.example.demo.student;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.student.models.MyStudent;
import com.example.demo.student.models.MyStudentDetails;


// Lets spring security know that the service exists 
// and it autwires the service to the security configuration
@Service
public class MyStudentDetailsService implements UserDetailsService{

    @Autowired
    MyStudentRepository myStudentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<MyStudent> student = myStudentRepository.findByStudentName(username);
        student.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return student.map(MyStudentDetails::new).get();
    }
}
