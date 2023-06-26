package com.example.brewquest.controllers;

import com.example.brewquest.models.Friend;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.FriendsRepository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
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

    @GetMapping("/addFriend")
    public String showAddFriendsForm(Model model) {
        model.addAttribute("friend", new Friend());
        return "addFriend";
    }

    @PostMapping("/addFriend")
    public String addFriend(Friend friend) {
        friendsDao.save(friend);
        return "redirect:/profile";
    }

    @GetMapping("/profile/{id}/friends")
    public String viewFriends(@PathVariable long id, Model model) {
        User user = usersDao.findById(id).orElse(null);
        if (user == null) {
            return "error";
        }

        List<Friend> friends = friendsDao.findAll();
        List<Friend> userFriends = new ArrayList<>();

        for (Friend friend : friends) {
            if (friend.getUser().getId().equals(id)) {
                userFriends.add(friend);
            }
        }
        model.addAttribute("friends", userFriends);
        return "redirect:/profile";
    }

    @GetMapping("/deleteFriend/{id}")
    public String deleteFriend(@PathVariable long id) {
        friendsDao.deleteById(id);
        return "redirect:/profile";
    }
}
