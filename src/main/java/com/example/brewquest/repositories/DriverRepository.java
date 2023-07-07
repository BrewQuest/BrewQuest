package com.example.brewquest.repositories;

import com.example.brewquest.models.Driver;
import com.example.brewquest.models.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository <Driver, Long> {
    Driver findByUser(User user);

}