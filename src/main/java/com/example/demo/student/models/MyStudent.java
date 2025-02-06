package com.example.demo.student.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mystudent")
public class MyStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String studentName;
    private String password;
    private boolean active;
    private String roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPassword() {
        return password;
    }   

    public void setPassword(String password) {
        this.password = password;
    }   

    public boolean isActive() {
        return active;
    }   

    public void setActive(boolean active) {
        this.active = active;
    }   

    public String getRoles() {
        return roles;
    }   

    public void setRoles(String roles) {
        this.roles = roles;
    }   

    @Override
    public String toString() {
        return "MyStudent [active=" + active + ", id=" + id + ", password=" + password + ", roles=" + roles
                + ", studentName=" + studentName + "]";
    }

    public MyStudent(int id, String studentName, String password, boolean active, String roles) {
        this.id = id;
        this.studentName = studentName;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public MyStudent() {
    }
}
