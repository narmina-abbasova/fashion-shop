package com.telran.fashionshop.controllers;

import com.telran.fashionshop.domain.entities.Product;
import com.telran.fashionshop.domain.repositories.ProductRepository;
import com.telran.fashionshop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LandingController {

    private final ProductRepository productRepository;

    private final CartService cartService;

    @GetMapping("/")
    public String landingPage(Model model) {
        // Simulate a list of products (you can fetch this from a database)
        List<Product> productList = productRepository.findAll();
        model.addAttribute("products", productList);

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", authentication.getName());

        boolean adminAuthentication = authentication.isAuthenticated()
                && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (adminAuthentication) {
            return "admin-landing";
        }

        boolean customerAuthentication = authentication.isAuthenticated()
                && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
        model.addAttribute("authenticated", customerAuthentication);
        if (customerAuthentication) {
            model.addAttribute("itemCountInCart", cartService.getCartItemCount());
        }
        return "customer-landing";
    }
}
