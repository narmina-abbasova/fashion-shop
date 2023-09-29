package com.telran.fashionshop.domain.repositories;

import com.telran.fashionshop.domain.entities.Order;
import com.telran.fashionshop.domain.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerIdOrderByPlacedAtDesc(String customerId);
}
