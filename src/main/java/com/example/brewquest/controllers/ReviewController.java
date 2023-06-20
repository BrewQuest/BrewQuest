package com.example.brewquest.controllers;

import com.example.brewquest.repositories.ReviewRepository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewController {
    private final ReviewRepository ReviewDaos;
    private final UserRepository UsersDaos;

    public ReviewController(ReviewRepository reviewDaos, UserRepository usersDaos) {
        ReviewDaos = reviewDaos;
        UsersDaos = usersDaos;
    }
    @GetMapping("create-review")

}
