package com.telran.fashionshop.domain.repositories;

import com.telran.fashionshop.domain.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {


}
