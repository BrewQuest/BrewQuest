package com.example.brewquest.repositories;

import com.example.brewquest.controllers.UserController;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, long> {
    User findByUsername(String username);
}
