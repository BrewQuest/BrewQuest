package com.example.brewquest.controllers;

import com.example.brewquest.models.Friend;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.FriendsRepository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FriendsController {
    private final UserRepository usersDao;
    private final FriendsRepository friendsDao;

    public FriendsController(UserRepository usersDao, FriendsRepository friendsDao) {
        this.usersDao = usersDao;
        this.friendsDao = friendsDao;
    }

    @GetMapping("/friends")
    public String getFriends(Model model) {
        List<Friend> friends = friendsDao.findAll();
        model.addAttribute("friends", friends);
        return "friends";
    }

    @GetMapping("/addFriend/{id}")
    public String addFriend(@PathVariable long id, Model model) {
        User user = usersDao.findById(id).orElse(null);
        if (user == null) {
            return "error";
        }

        Friend newFriend = new Friend();
        newFriend.setUser(user);
        User friendUser = usersDao.findById(id).orElse(null);
        newFriend.setFriend(friendUser);

        friendsDao.save(newFriend);

        return "redirect:/profile/" + id;
    }

    @GetMapping("/deleteFriend/{id}")
    public String deleteFriend(@PathVariable long id) {
        friendsDao.deleteById(id);
        return "redirect:/friends";
    }
}
