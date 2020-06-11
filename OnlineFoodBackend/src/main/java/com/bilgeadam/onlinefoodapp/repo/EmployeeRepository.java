package com.bilgeadam.onlinefoodapp.repo;

import com.bilgeadam.onlinefoodapp.domain.Employee;
import com.bilgeadam.onlinefoodapp.domain.Meal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Optional<Employee> findById(Long empId);
    Optional<Employee> findByUsername(String username);
}
