package com.example.brewquest.repositories;

import com.example.brewquest.models.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsRepository extends JpaRepository <Friend, Long> {
}
