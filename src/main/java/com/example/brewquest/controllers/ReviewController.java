package com.example.brewquest.controllers;

import com.example.brewquest.models.Driver;
import com.example.brewquest.models.Review;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.DriverRepository;
import com.example.brewquest.repositories.ReviewRepository;
import com.example.brewquest.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ReviewController {
    private final ReviewRepository reviewDaos;
    private final UserRepository usersDaos;

    private final DriverRepository driverDao;

    public ReviewController(ReviewRepository reviewDaos, UserRepository usersDaos, DriverRepository driverDao) {
        this.reviewDaos = reviewDaos;
        this.usersDaos = usersDaos;
        this.driverDao = driverDao;
    }
    // using string of id due to API "id"
    @GetMapping("/brewery/{id}/create-review")
    public String showCreateForm(@PathVariable String id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Driver> drivers = driverDao.findAll();
        String driverCheck = "";
        for(Driver driver : drivers) {
            if (driver.getUser().getId() == user.getId()) {
                System.out.println("running");
                driverCheck = "true";
                break;
            }
        }
        model.addAttribute("driverCheck", driverCheck);
        String brewId = id;
        model.addAttribute("brewId", brewId);
        System.out.println(brewId);
        model.addAttribute("review", new Review());
        return "Reviews/Create-Review";
    }
    // create the new review and send back to brewery page
    @PostMapping("/brewery/create-review")
    public String CreateFormProcess(@ModelAttribute Review review) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("Brewery ID Only: " + review.getBreweryId());

        Review newReview = new Review();
        newReview.setUser(user);
        newReview.setRating(review.getRating());
        newReview.setDescription(review.getDescription());
        newReview.setPassengers(review.getPassengers());
        newReview.setBreweryId(review.getBreweryId());
        System.out.println("New Review Object: " + newReview.getBreweryId());
        reviewDaos.save(newReview);

        return "redirect:/profile/" + user.getId() + "/reviews";
    }
@GetMapping("/review/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model){
        if (reviewDaos.findById(id).isPresent()) {
            Review review = reviewDaos.findById(id).get();
            model.addAttribute("review", review);
        }
        String reviewDescription= reviewDaos.findById(id).get().getDescription();
        model.addAttribute("description", reviewDescription);
        return "Reviews/Edit-Review";
}
@PostMapping("/review/{id}/edit")
    public String updateReview(@PathVariable long id, @ModelAttribute Review review){
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Review editReview = reviewDaos.findById(id).get();
        editReview.setRating(review.getRating());
        editReview.setDescription(review.getDescription());
        reviewDaos.save(editReview);
        return "redirect:/profile/" + user.getId() + "/reviews";

}
    @PostMapping("/review/{id}/delete")
    public String deleteReview(@PathVariable("id") long id) {
        reviewDaos.deleteById(id);
        return "redirect:/profile/" + reviewDaos.findById(id).get().getUser().getId() + "/reviews";
    }

    @GetMapping("profile/{id}/reviews")
    public String viewReviews(@PathVariable long id, Model model) {
        User user = usersDaos.findById(id).get();
        Driver driver = driverDao.findByUser(user);
        Long userId = user.getId();
        List<Review> reviews = reviewDaos.findAll();
        List<Review> userReviews = new ArrayList<>();
        Integer passengers = 0;

        for (Review review : reviews) {
            if (review.getUser().getId().equals(userId)) {
               passengers += review.getPassengers();
               userReviews.add(review);
            }
        }
        Integer totalReviews = userReviews.size();
        user.setTotalBreweries(totalReviews);
        model.addAttribute("reviews", userReviews);
        if(driver != null) {
            driver.setTotalPassengers(passengers);
            driverDao.save(driver);
        }
        usersDaos.save(user);
        return"reviewpage";
    }
}