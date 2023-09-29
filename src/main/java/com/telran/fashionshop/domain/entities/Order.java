package com.telran.fashionshop.domain.entities;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Value
@Builder
public class Order {
    @Id
    String id;
    String customerId;
    List<Product> products;
    BigDecimal totalAmount;
    String address;
    String phoneNumber;
    Date placedAt;
}


