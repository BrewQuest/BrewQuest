package com.example.brewquest.controllers;

import com.example.brewquest.models.Friend;
import com.example.brewquest.models.Review;
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
<<<<<<< HEAD
//    @PostMapping("/addfriends")
//    public String addFriend(Model model) {
//        Long userId = (Long) model.getAttribute("userId");
//        User newAddfriend = usersDao.findById(userId).get();
//
//        Friend newFriend = new Friend(gathering session user, newAddfriend);
//        return
//    }


    @GetMapping("/profile/{id}/friends")
    public String viewFriends(@PathVariable long id, Model model) {
        User user = usersDao.findById(id).get();
        Long userId = user.getId();
        List<Friend> friends = friendsDao.findAll();
        List<Friend> userFriends = new ArrayList<>();

        for (Friend friend : friends) {
            if (friend.getUser().getId().equals(userId)) {
                userFriends.add(friend);
            }
        }
        model.addAttribute("friends", userFriends);
        return "users/friends";
    }


}
