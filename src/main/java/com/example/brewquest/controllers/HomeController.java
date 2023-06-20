package com.example.brewquest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "landingpage";
    }

    @GetMapping("/home")
        public String homePage(){
            return "HomePage";
    }
}
