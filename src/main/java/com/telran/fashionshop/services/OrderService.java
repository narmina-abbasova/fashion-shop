package com.telran.fashionshop.services;

import com.telran.fashionshop.domain.entities.Cart;
import com.telran.fashionshop.domain.entities.Order;
import com.telran.fashionshop.domain.entities.Product;
import com.telran.fashionshop.domain.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public Order placeOrder(final String address, final String phoneNumber) {
        final Cart cart = cartService.getActiveCart().orElseThrow();
        final Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .placedAt(new Date())
                .address(address)
                .phoneNumber(phoneNumber)
                .customerId(cart.getCustomerId())
                .products(cart.getItems())
                .totalAmount(cart.getItems().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
        orderRepository.save(order);
        cartService.cleanup();
        return order;
    }

    public List<Order> getOrders() {
        return orderRepository.findByCustomerIdOrderByPlacedAtDesc(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
