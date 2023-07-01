package com.example.brewquest.repositories;

import com.example.brewquest.models.Friend;
import com.example.brewquest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendsRepository extends JpaRepository <Friend, Long> {
    Friend findByUserAndFriend(User loggedInUser, User user);

    List<Friend> findByUser(User loggedInUser);
}
