package com.bilgeadam.onlinefoodapp.service;

import com.bilgeadam.onlinefoodapp.domain.Employee;
import com.bilgeadam.onlinefoodapp.domain.Meal;
import com.bilgeadam.onlinefoodapp.repo.EmployeeServiceImpl;
import com.bilgeadam.onlinefoodapp.repo.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final EmployeeServiceImpl employeeService;

    @Autowired
    public MealService(MealRepository mealRepository, EmployeeServiceImpl employeeService) {
        this.mealRepository = mealRepository;
        this.employeeService = employeeService;
    }

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public void delete(String code) {
        mealRepository.deleteById(code);
    }

    public Optional<Meal> findByCode(String code) {
        return mealRepository.findByCode(code);
    }

    public Meal save(Meal meal) {
        Optional<Employee> employee = employeeService.findById(1L);
        employee.ifPresent(meal::setEmployee);
        return mealRepository.save(meal);
    }

    public List<Meal> getCampaignMeals() {
        return mealRepository.findAllByCampaignTrue();
    }

}
