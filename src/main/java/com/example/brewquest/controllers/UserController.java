package com.example.brewquest.controllers;


import com.example.brewquest.models.Driver;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.DriverRepository;
import com.example.brewquest.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;

    private final DriverRepository driverDao;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, DriverRepository driverDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.driverDao = driverDao;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    @GetMapping("/aboutus")
    public String aboutUs() {
        return "aboutUs";
    }

//    @PostMapping("/sign-up")
//    public String saveUser(@ModelAttribute User user){
//        String hash = passwordEncoder.encode(user.getPassword());
//        user.setPassword(hash);
//        userDao.save(user);
//        return "redirect:/login";
//    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/sign-up";
        } else {
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            userDao.save(user);
            return "redirect:/login";
        }

        // Save the user and perform further processing
        // ...

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
        return "redirect:/profile/" + id;
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
        users.sort(Comparator.comparing(User::getTotalBreweries).reversed());
        List<Driver> drivers = driverDao.findAll();
        model.addAttribute("drivers", drivers);
        drivers.sort(Comparator.comparing(Driver::getTotalPassengers).reversed());

        return "leaderboard";
    }


}
