package com.bilgeadam.onlinefoodapp.repo;

import com.bilgeadam.onlinefoodapp.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, Long> {

    Optional<Customer> findById(Long id);

    Optional<Customer> findByName (String name);

}
