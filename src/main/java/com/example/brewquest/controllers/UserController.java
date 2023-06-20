package com.example.brewquest.controllers;


import com.example.brewquest.models.Driver;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.Driver_repository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;

    private final Driver_repository driverDao;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, Driver_repository driverDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.driverDao = driverDao;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        User user = userDao.findById(id).get();
        model.addAttribute("user", user);
        return "users/profile";
    }

    @GetMapping("/profile/{id}/edit")
    public String showEditProfile(@PathVariable Long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user.getId() == userDao.findById(id).get().getId()) {
            User userProfile = userDao.findById(id).get();
            model.addAttribute("user", userProfile);
            return "users/edit";
        } else {
            return "redirect:/home";
        }

    }

    @PostMapping("/profile/{id}/edit")
    public String editProfile(@PathVariable Long id, @ModelAttribute User user){
        User editUser = userDao.findById(id).get();
        editUser.setFirstName(user.getFirstName());
        editUser.setLastName(user.getLastName());
        editUser.setEmail(user.getEmail());
        editUser.setZipcode(user.getZipcode());
        editUser.setUsername(user.getUsername());
        editUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.save(editUser);
        return "redirect:/profile/1";
    }

    @PostMapping("/profile/{id}/delete")
    public String deleteProfile(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user.getId() == userDao.findById(id).get().getId()) {
            userDao.deleteById(id);
        }

        return "redirect:/home";
    }

    @GetMapping("/leaderboard")
    public String viewLeaderboard(Model model) {
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        List<Driver> drivers = driverDao.findAll();
        model.addAttribute("drivers", drivers);
        return "/leaderboard";
    }


}
