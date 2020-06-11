package com.bilgeadam.onlinefoodapp.controller;

import com.bilgeadam.onlinefoodapp.domain.Employee;
import com.bilgeadam.onlinefoodapp.domain.HelloWorldBean;
import com.bilgeadam.onlinefoodapp.domain.Role;
import com.bilgeadam.onlinefoodapp.repo.EmployeeService;
import com.bilgeadam.onlinefoodapp.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*") // bunu sonra ekleyeceksin
@RestController
@RequestMapping("/initial")
public class HelloWorldController {

    private RoleService roleService;
    private EmployeeService employeeService;

    public HelloWorldController(RoleService roleService, EmployeeService employeeService) {
        this.roleService = roleService;
        this.employeeService = employeeService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/helloworldbean/{name}")
    public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World %s", name));
        //throw new RuntimeException("Error occured!");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/roleName/{name}")
    public String roleBean(@PathVariable String name) {
        String roles = "";
        Optional<Employee> emp = employeeService.findByUsername(name);
        if (emp.isPresent()) {
            Optional<Role> roleOp = roleService.findById(emp.get().getEmpId());
            if (roleOp.isPresent()) {
                Role role = roleOp.get();
                roles = role.getName();
            }
        }
        return roles;
    }

}
