package com.bilgeadam.onlinefoodapp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EXPLANATION")
    private String explanation;

    @ManyToMany(mappedBy = "roles")
    @JsonManagedReference
    private Set<Employee> employees;

    public Role() {
    }

    public Role(Long roleId, String name, String explanation, Set<Employee> employees) {
        this.roleId = roleId;
        this.name = name;
        this.explanation = explanation;
        this.employees = employees;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
