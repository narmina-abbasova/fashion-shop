package com.telran.fashionshop.services;

import com.telran.fashionshop.domain.entities.Role;
import com.telran.fashionshop.domain.entities.User;
import com.telran.fashionshop.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void register(final String username, final String password, final String email) {
        if (userRepository.existsById(username)) {
            throw new RuntimeException("User already exists");
        }
        final User customer = new User(username, passwordEncoder.encode(password), email, List.of(Role.CUSTOMER));
        userRepository.save(customer);
    }
}
