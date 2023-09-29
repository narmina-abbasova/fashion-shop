package com.telran.fashionshop.domain.repositories;

import com.telran.fashionshop.domain.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {


}
