
package com.example.brewquest.controllers;


import com.example.brewquest.models.Driver;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.Driver_repository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DriverController {
    private final Driver_repository driverRepository;
    private final UserRepository usersDao;

    public DriverController(Driver_repository driverRepository, UserRepository usersDao) {
        this.driverRepository = driverRepository;
        this.usersDao = usersDao;
    }


    @GetMapping("/signup-driver")
    public String showSignupForm(Model model) {
        model.addAttribute("driver", new Driver());
        return "/Driver/sign-up-driver";
    }

    @PostMapping("/signup-driver")
    public String processSignupForm(@ModelAttribute Driver driver) {
        User user = usersDao.findById(1L).get();
        driver.setUser(user);
        driver.setCarMake(driver.getCarMake());
        driver.setCarModel(driver.getCarModel());
        driver.setLicensePlateNum(driver.getLicensePlateNum());
        driver.setDriversLicenseNum(driver.getDriversLicenseNum());

        driverRepository.save(driver);
        return "redirect:/";
    }
}

