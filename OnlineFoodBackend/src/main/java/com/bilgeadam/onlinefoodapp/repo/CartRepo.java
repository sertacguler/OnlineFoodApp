package com.bilgeadam.onlinefoodapp.repo;

import com.bilgeadam.onlinefoodapp.domain.Cart;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    List<Cart> findAll();

    Cart save(Cart cart);

    Optional<Cart> findByCustomer_Id(Long id);

    Cart findByCustomer_IdAndStatusIsFalse(Long id);

    List<Cart> findByCustomer_IdAndStatusIsTrue(Long id);

}
