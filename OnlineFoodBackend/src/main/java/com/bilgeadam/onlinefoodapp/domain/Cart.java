package com.bilgeadam.onlinefoodapp.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CART")
public class Cart {

    @Id
    @Column(name = "CART_ID")

    @SequenceGenerator(name = "SeqGenerator", sequenceName = "cart_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqGenerator")

    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long cart_id;

    @Column(name = "TOTAL_PRICE")
    private String totalPrice;

    @Column(name = "STATUS",nullable = false)
    private Boolean status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "CART_MEAL",
            joinColumns = @JoinColumn(name = "CART_ID"),
            inverseJoinColumns = @JoinColumn(name = "CODE")
    )
    List<Meal> meals = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonManagedReference
    private Customer customer;

    public Long getCart_id() {
        return cart_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
