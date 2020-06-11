package com.bilgeadam.onlinefoodapp.service;

import com.bilgeadam.onlinefoodapp.domain.Cart;
import com.bilgeadam.onlinefoodapp.domain.Delivery;
import com.bilgeadam.onlinefoodapp.repo.DeliveryRepo;
import org.springframework.stereotype.Service;

import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    private DeliveryRepo deliveryRepo;

    public DeliveryService(DeliveryRepo deliveryRepo) {
        this.deliveryRepo = deliveryRepo;
    }

    public Delivery deliverySave(Delivery delivery) {
        return deliveryRepo.save(delivery);
    }

    public Delivery findByDeliveryStatusIsFalseAndCart(Cart cart) {
        return deliveryRepo.findByDeliveryStatusIsFalseAndCart(cart);
    }

    public Delivery findByDeliveryStatusIsTrueAndCart(Cart cart) {
        return deliveryRepo.findByDeliveryStatusIsTrueAndCart(cart);
    }

    public List<Delivery> findByDeliveryStatusIsFalse() {
        return deliveryRepo.findByDeliveryStatusIsFalse();
    }

    public List<Delivery> findByDeliveryStatusIsTrue() {
        return deliveryRepo.findByDeliveryStatusIsTrue();
    }

    public List<Delivery> findAll() {
        return deliveryRepo.findAll();
    }

    public Optional<Delivery> findById(Long id) {
        return deliveryRepo.findById(id);
    }

}
