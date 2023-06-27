package com.example.brewquest.controllers;

import com.example.brewquest.models.Review;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.Driver_repository;
import com.example.brewquest.repositories.ReviewRepository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewController {
    private final ReviewRepository reviewDaos;
    private final UserRepository usersDaos;

    private final Driver_repository driverDao;

    public ReviewController(ReviewRepository reviewDaos, UserRepository usersDaos, Driver_repository driverDao) {
        this.reviewDaos = reviewDaos;
        this.usersDaos = usersDaos;
        this.driverDao = driverDao;
    }
    // using string of id due to API "id"
    @GetMapping("/brewery/{id}/create-review")
    public String showCreateForm(@PathVariable String id, Model model){
        String brewId = id;
        model.addAttribute("brewId", brewId);
        System.out.println(brewId);
        model.addAttribute("review", new Review());
        return "/Reviews/Create-Review";
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
        user.setTotalBreweries(user.getTotalBreweries());
        usersDaos.save(user);

        return "redirect:/brewery/" + newReview.getBreweryId();
    }
@GetMapping("/review/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model){
        if (reviewDaos.findById(id).isPresent()) {
            Review review = reviewDaos.findById(id).get();
            model.addAttribute("review", review);
        }
        String reviewDescription= reviewDaos.findById(id).get().getDescription();
        model.addAttribute("description", reviewDescription);
        return "/Reviews/Edit-Review";
}
@PostMapping("/review/{id}/edit")
    public String updateReview(@PathVariable long id, @ModelAttribute Review review){
        User user = usersDaos.findById(id).get();
        Review editReview = reviewDaos.findById(id).get();
        editReview.setBreweryId(review.getBreweryId());
        editReview.setRating(review.getRating());
        editReview.setDescription(review.getDescription());
        editReview.setPassengers(review.getPassengers());
        reviewDaos.save(editReview);
        return "/profile/" + user.getId() + "/reviews";
}
    @PostMapping("/review/{id}/delete")
    public String deleteReview(@PathVariable("id") long id) {
        reviewDaos.deleteById(id);
        return "redirect:/profile/" + id + "/reviews";
    }

    @GetMapping("profile/{id}/reviews")
    public String viewReviews(@PathVariable long id, Model model) {
        User user = usersDaos.findById(id).get();
        Long userId = user.getId();
        List<Review> reviews = reviewDaos.findAll();
        List<Review> userReviews = new ArrayList<>();

        for (Review review : reviews) {
            if (review.getUser().getId().equals(userId)) {
                userReviews.add(review);
            }
        }
        model.addAttribute("reviews", userReviews);
        return"reviewpage";
    }
}