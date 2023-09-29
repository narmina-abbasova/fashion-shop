package com.telran.fashionshop.domain.entities;

import lombok.Value;
import org.springframework.data.annotation.Id;

import java.util.List;

@Value
public class User {
    @Id
    String username;
    String password;
    String email;
    List<Role> roles;
}
