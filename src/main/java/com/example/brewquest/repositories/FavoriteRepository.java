package com.example.brewquest.repositories;

import com.example.brewquest.models.Favorite;
import com.example.brewquest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
}
