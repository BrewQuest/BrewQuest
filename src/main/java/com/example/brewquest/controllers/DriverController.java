
package com.example.brewquest.controllers;


import com.example.brewquest.models.Driver;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.Driver_repository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DriverController {
    private final Driver_repository driversDao;
    private final UserRepository usersDao;

    public DriverController(Driver_repository driversDao, UserRepository usersDao) {
        this.driversDao = driversDao;
        this.usersDao = usersDao;
    }


    @GetMapping("/signup-driver")
    public String showSignupForm(Model model) {
        model.addAttribute("driver", new Driver());
        return "/Driver/sign-up-driver";
    }

    @PostMapping("/signup-driver")
    public String processSignupForm(@ModelAttribute Driver driver) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        driver.setUser(user);
        driver.setCarMake(driver.getCarMake());
        driver.setCarModel(driver.getCarModel());
        driver.setLicensePlateNum(driver.getLicensePlateNum());
        driver.setDriversLicenseNum(driver.getDriversLicenseNum());

        driversDao.save(driver);
        return "redirect:/";
    }

    @GetMapping("/driver/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        if (driversDao.findById(id).isPresent()) {
            Driver driverToEdit = driversDao.findById(id).get();
            model.addAttribute("driver", driverToEdit);  // Make sure the attribute name is "driver"
        }
        return "Driver/edit-driver";
    }

    @PostMapping("/driver/{id}/edit")
    public String updateDriver(@PathVariable long id, @ModelAttribute Driver driver) {
        User user = usersDao.findById(id).get();
        driver.setUser(user);
        driver.setCarMake(driver.getCarMake());
        driver.setCarModel(driver.getCarModel());
        driver.setLicensePlateNum(driver.getLicensePlateNum());
        driver.setDriversLicenseNum(driver.getDriversLicenseNum());
        driversDao.save(driver);
        return "users/profile";
    }

    @PostMapping("/driver/{id}/delete")
    public String deleteDriver(@PathVariable("id") long id) {
        driversDao.deleteById(id);
        return "users/profile";
    }
}




