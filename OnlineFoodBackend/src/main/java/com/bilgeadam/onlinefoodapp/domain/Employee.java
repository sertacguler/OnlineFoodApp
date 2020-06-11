package com.bilgeadam.onlinefoodapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @Column(name = "EMP_ID")
    private Long empId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "USERNAME")
    @JsonIgnore
    private String username;

    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "EMPLOYEE_ROLE",
            joinColumns = @JoinColumn(name = "EMP_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    @JsonBackReference
    private Set<Role> roles;

    public Employee() {
    }

    public Employee(Long empId, String name, String surname, String username, String password, Set<Role> roles) {
        this.empId = empId;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
