package com.bilgeadam.onlinefoodapp.repo;

import com.bilgeadam.onlinefoodapp.domain.Employee;

import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> findById(Long empId);
    Optional<Employee> findByUsername(String username);
}
