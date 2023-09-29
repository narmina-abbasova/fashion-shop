package com.telran.fashionshop.domain.entities;

import lombok.Value;
import org.springframework.data.annotation.Id;

import java.util.List;
@Value
public class Cart {
     @Id
     String id;
     String customerId;
     List<Product> items;

}
