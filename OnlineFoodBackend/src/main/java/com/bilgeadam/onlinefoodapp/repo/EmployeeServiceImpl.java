package com.bilgeadam.onlinefoodapp.repo;

import com.bilgeadam.onlinefoodapp.dao.EmployeeDaoImpl;
import com.bilgeadam.onlinefoodapp.domain.Employee;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDaoImpl employeeDao;

    public EmployeeServiceImpl(EmployeeDaoImpl employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Optional<Employee> findById(Long empId) {
        return employeeDao.findById(empId);
    }

    @Override
    public Optional<Employee> findByUsername(String username) {
        return employeeDao.findByUsername(username);
    }
}
