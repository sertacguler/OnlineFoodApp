package com.bilgeadam.onlinefoodapp.jwt;

import com.bilgeadam.onlinefoodapp.domain.Employee;
import com.bilgeadam.onlinefoodapp.repo.EmployeeServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsAdminService implements UserDetailsService {

    private final EmployeeServiceImpl employeeService;

    public JwtUserDetailsAdminService(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> userFromDB = employeeService.findByUsername(username);
        JwtUserDetails jwtUserDetails = null;
        if (userFromDB.isPresent()) {
            jwtUserDetails = new JwtUserDetails(userFromDB.get());
        } else {
            throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
        }

        return jwtUserDetails;
    }
}



















