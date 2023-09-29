package com.telran.fashionshop.controllers;

import com.telran.fashionshop.domain.entities.Product;
import com.telran.fashionshop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart-items")
    public String addToCartFromLandingPage(@RequestParam("productId") final String productId) {
        cartService.addProduct(productId);
        return "redirect:/"; // Change this to your desired page
    }

    @PostMapping("/cart-item-addition")
    public String addToCart(@RequestParam("productId") final String productId) {
        cartService.addProduct(productId);
        return "redirect:/cart"; // Change this to your desired page
    }

    @PostMapping("/cart-item-removal")
    public String removeFromCart(@RequestParam("productId") final String productId) {
        cartService.removeProduct(productId);
        return "redirect:/cart"; // Change this to your desired page
    }

    @GetMapping("/cart")
    public String viewCart(final Model model) {
        // Dummy cart data for demonstration
        List<Product> cartProducts = cartService.getActiveCart().orElseThrow().getItems();
        model.addAttribute("products", cartProducts);
        return "cart";
    }
}
