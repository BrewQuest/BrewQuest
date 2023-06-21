package com.example.brewquest.controllers;

import com.example.brewquest.models.Friend;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.FriendsRepository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FriendsController {
    private final UserRepository usersDao;
    private final FriendsRepository friendsDao;

    public FriendsController(UserRepository usersDao, FriendsRepository friendsDao) {
        this.usersDao = usersDao;
        this.friendsDao = friendsDao;
    }
//    @PostMapping("/addfriends")
//    public String addFriend(Model model) {
//        Long userId = (Long) model.getAttribute("userId");
//        User newAddfriend = usersDao.findById(userId).get();
//
//        Friend newFriend = new Friend(gathering session user, newAddfriend);
//        return
//    }


}
