package com.telran.fashionshop.services;

import com.telran.fashionshop.domain.entities.Cart;
import com.telran.fashionshop.domain.entities.Product;
import com.telran.fashionshop.domain.repositories.CartRepository;
import com.telran.fashionshop.domain.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    private String getCustomerUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    public Optional<Cart> getActiveCart() {
        return cartRepository.findCartByCustomerId(getCustomerUsername());
    }

    public int getCartItemCount() {
        final Optional<Cart> cart = getActiveCart();
        return cart.map(c -> c.getItems().size()).orElse(0);
    }

    public int addProduct(final String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        final Optional<Cart> cart = getActiveCart();
        if(cart.isPresent()) {
            cart.get().getItems().add(product);
            cartRepository.save(cart.get());
            return cart.get().getItems().size();
        } else {
            final Cart newCart = new Cart(
                    UUID.randomUUID().toString(),
                    getCustomerUsername(),
                    List.of(product)
                    );
            cartRepository.save(newCart);
            return 1;
        }
    }

    public int removeProduct(final String productId) {
        final Cart cart = getActiveCart().orElseThrow();
        final Product product = cart.getItems().stream()
                .filter(i -> Objects.equals(productId, i.getId()))
                .findAny()
                .orElseThrow();
        cart.getItems().remove(product);
        cartRepository.save(cart);
        return 0;
    }
    public void cleanup(){
        final Cart cart = getActiveCart().orElseThrow();
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
