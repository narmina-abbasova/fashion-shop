package com.telran.fashionshop.domain.repositories;

import com.telran.fashionshop.domain.entities.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findCartByCustomerId(String username);

}
