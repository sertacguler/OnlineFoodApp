package com.bilgeadam.onlinefoodapp.dto;

import com.bilgeadam.onlinefoodapp.domain.Meal;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    List<Meal> meals = new ArrayList<>();

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
