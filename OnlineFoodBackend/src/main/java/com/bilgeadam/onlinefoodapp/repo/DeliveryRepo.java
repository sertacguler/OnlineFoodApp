package com.bilgeadam.onlinefoodapp.repo;

import com.bilgeadam.onlinefoodapp.domain.Cart;
import com.bilgeadam.onlinefoodapp.domain.Delivery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepo extends CrudRepository<Delivery, Long> {

    Delivery save(Delivery delivery);

    Delivery findByDeliveryStatusIsFalseAndCart(Cart cart);

    Delivery findByDeliveryStatusIsTrueAndCart(Cart cart);

    List<Delivery> findByDeliveryStatusIsFalse();

    List<Delivery> findByDeliveryStatusIsTrue();

    List<Delivery> findAll();

    Optional<Delivery> findByCart(Cart cart);

    Optional<Delivery> findById(Long id);

}
