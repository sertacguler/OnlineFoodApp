package com.bilgeadam.onlinefoodapp.service;

import com.bilgeadam.onlinefoodapp.domain.Customer;
import com.bilgeadam.onlinefoodapp.repo.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepo customerRepo;

    public CustomerService(CustomerRepo cutomerRepo) {
        this.customerRepo = cutomerRepo;
    }

    public Optional<Customer> findById(Long id) {
        return customerRepo.findById(id);
    }

    public Optional<Customer> findByName(String name) {
        return customerRepo.findByName(name);
    }

}
