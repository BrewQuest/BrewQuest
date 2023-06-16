
package com.example.brewquest.controllers;


import com.example.brewquest.models.Driver;
import com.example.brewquest.repositories.Driver_repository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DriverController {
    private final Driver_repository driverRepository;

    public DriverController(Driver_repository driverRepository) {
        this.driverRepository = driverRepository;
    }


    @GetMapping("/signup-driver")
    public String showSignupForm(Model model) {
        model.addAttribute("driver", new Driver());
        return "/Driver/sign-up-driver";
    }

    @PostMapping("/signup-driver")
    public String processSignupForm(@ModelAttribute("driver") Driver driver, BindingResult result) {
        if (result.hasErrors()) {
            return "/Driver/sign-up-driver";
        }

        driverRepository.save(driver);
        return "redirect:/profile";
    }
//    test
}

