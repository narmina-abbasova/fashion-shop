package com.telran.fashionshop.domain.repositories;

import com.telran.fashionshop.domain.entities.Role;
import com.telran.fashionshop.domain.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testAdminUserIsPresent() {
        Optional<User> admin = userRepository.findById("admin");
        assertTrue(admin.isPresent());
        assertEquals("admin", admin.get().getUsername());
        assertEquals("admin@fashion.shop", admin.get().getEmail());
        assertTrue(new BCryptPasswordEncoder().matches("123456", admin.get().getPassword()));
        assertIterableEquals(List.of(Role.ADMIN), admin.get().getRoles());
    }
}