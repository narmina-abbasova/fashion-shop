package com.telran.fashionshop.domain.entities;


import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class Product {
    @Id
    String id;
    String name;
    String description;
    BigDecimal price;
    String size;
    String imageUrl;

}


