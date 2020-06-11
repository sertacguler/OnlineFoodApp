package com.bilgeadam.onlinefoodapp.repo;

import com.bilgeadam.onlinefoodapp.domain.Meal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MealRepository extends CrudRepository<Meal, String> {
    List<Meal> findAll();

    void deleteById(String code);

    Optional<Meal> findByCode(String code);

    List<Meal> findAllByCampaignTrue();

}
