package com.bilgeadam.onlinefoodapp.dao;

import com.bilgeadam.onlinefoodapp.domain.Employee;

import java.util.Optional;

public interface EmployeeDao {
    Optional<Employee> findById(Long empId);

    Optional<Employee> findByUsername(String username);
}
