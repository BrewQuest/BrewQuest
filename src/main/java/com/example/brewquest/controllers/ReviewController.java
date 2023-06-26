package com.example.brewquest.controllers;

import com.example.brewquest.models.Review;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.ReviewRepository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewController {
    private final ReviewRepository reviewDaos;
    private final UserRepository usersDaos;

    public ReviewController(ReviewRepository reviewDaos, UserRepository usersDaos) {
        this.reviewDaos = reviewDaos;
        this.usersDaos = usersDaos;
    }
    // using string of id due to API "id"
    @GetMapping("/brewery/{id}/create-review")
    public String showCreateForm(@PathVariable String id, Model model){
        model.addAttribute("breweryId", id);
    model.addAttribute("review", new Review());
    return "/Reviews/Create-Review";
    }
    // using string of id due to API "id"
@PostMapping("/brewery/{id}/create-review")
    public String CreateFormProcess(@PathVariable String id, @ModelAttribute Review review){
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    review.setUser(user);
    review.setRating(review.getRating());
    review.setDescription(review.getDescription());
    review.setPassengers(review.getPassengers());
    review.setBreweryId(id);

    reviewDaos.save(review);
    return "redirect:/brewery/" + id;
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
        review.setUser(user);
        review.setBreweryId(review.getBreweryId());
        review.setRating(review.getRating());
        review.setDescription(review.getDescription());
        review.setPassengers(review.getPassengers());
        return "/";
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