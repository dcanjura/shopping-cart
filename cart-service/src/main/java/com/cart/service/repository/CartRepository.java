package com.cart.service.repository;

import com.cart.service.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    Cart getCartById(Long id);

    List<Cart> findByUserId(String userId);
}
