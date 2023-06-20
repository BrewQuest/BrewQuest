package com.example.brewquest.controllers;

import com.example.brewquest.models.Review;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.ReviewRepository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {
    private final ReviewRepository ReviewDaos;
    private final UserRepository UsersDaos;

    public ReviewController(ReviewRepository reviewDaos, UserRepository usersDaos) {
        ReviewDaos = reviewDaos;
        UsersDaos = usersDaos;
    }
    @GetMapping("/create-review")
    public String showCreateForm(Model model){
    model.addAttribute("review", new Review());
    return "/Reviews/Create-Review";
    }
@PostMapping("/create-review")
    public String CreateFormProcess(@ModelAttribute Review review){
    User user = UsersDaos.findById(1L).get();
    review.setUser(user);
    review.setBreweryId(review.getBreweryId());
    review.setRating(review.getRating());
    review.setDescription(review.getDescription());
    review.setPassengers(review.getPassengers());

    ReviewDaos.save(review);
    return "redirect:/";
}

}
