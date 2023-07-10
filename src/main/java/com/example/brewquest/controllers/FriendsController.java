package com.example.brewquest.controllers;

import com.example.brewquest.models.Driver;
import com.example.brewquest.models.Friend;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.DriverRepository;
import com.example.brewquest.repositories.FriendsRepository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FriendsController {
    private final UserRepository usersDao;
    private final FriendsRepository friendsDao;

    private final DriverRepository driversDao;

    public FriendsController(UserRepository usersDao, FriendsRepository friendsDao, DriverRepository driversDao) {
        this.usersDao = usersDao;
        this.friendsDao = friendsDao;
        this.driversDao = driversDao;
    }

    @GetMapping("/friends")
    public String showFriends(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Friend> friends = friendsDao.findByUser(loggedInUser);
        List<Driver> drivers = driversDao.findAll();
        model.addAttribute("friends", friends);
        return "Friends";
    }

    @GetMapping("/addFriend/{id}")
    public String addFriend(@PathVariable("id") long id, Model model) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = usersDao.findById(id).orElse(null);

        if (user == null) {
            return "bruh";
        }

        // Save the authenticated user if it's not already saved
        if (authenticatedUser.getId() == null) {
            usersDao.save(authenticatedUser);
        }

        Friend newFriend = new Friend();
        newFriend.setUser(authenticatedUser);
        newFriend.setFriend(user);

        friendsDao.save(newFriend);

        return "redirect:/profile/" + id;
    }


    @GetMapping("/deleteFriend/{id}")
    public String deleteFriend(@PathVariable("id") long id) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User friendUser = usersDao.findById(id).orElse(null);

        if (friendUser != null) {
            Friend friend = friendsDao.findByUserAndFriend(authenticatedUser, friendUser);
            if (friend != null) {
                friendsDao.delete(friend);
            }
        }

        return "redirect:/profile/" + id;
    }
}
