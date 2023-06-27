package com.example.brewquest.controllers;
import com.example.brewquest.models.PasswordForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class PasswordController {

    @GetMapping("/password")
    public String showPasswordForm(Model model) {
        model.addAttribute("passwordForm", new PasswordForm());
        return "password";
    }

    @PostMapping("/validate")
    public String validatePassword(@Valid @ModelAttribute("passwordForm") PasswordForm passwordForm,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "password";
        }
        // Password is valid, perform further processing
        return "success";
    }
}
