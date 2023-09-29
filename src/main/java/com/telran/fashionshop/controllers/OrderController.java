package com.telran.fashionshop.controllers;

import com.telran.fashionshop.domain.entities.Order;
import com.telran.fashionshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public String placeOrder(@RequestParam("address") final String address,
                             @RequestParam("phoneNumber") final String phoneNumber,
                             final Model model) {
        final Order order = orderService.placeOrder(address, phoneNumber);
        model.addAttribute("order", order);
        return "order";
    }


}
