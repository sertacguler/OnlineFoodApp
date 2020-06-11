package com.bilgeadam.onlinefoodapp.service;

import com.bilgeadam.onlinefoodapp.domain.Cart;
import com.bilgeadam.onlinefoodapp.repo.CartRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private CartRepo cartRepo;

    public CartService(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }


    public List<Cart> findAllByCart() {
        return cartRepo.findAll();
    }

    public Cart save(Cart cart) {
        return cartRepo.save(cart);
    }

    public Optional<Cart> findAllByCart(Long id) {
        return cartRepo.findById(id);
    }

    public Optional<Cart> findByCustomer_Id(Long id) {
        return cartRepo.findByCustomer_Id(id);
    }

    public Cart findByCustomer_IdAndStatusIsFalse(Long id) {
        return cartRepo.findByCustomer_IdAndStatusIsFalse(id);
    }

    public List<Cart> findByCustomer_IdAndStatusIsTrue(Long id) {
        return cartRepo.findByCustomer_IdAndStatusIsTrue(id);
    }

}
