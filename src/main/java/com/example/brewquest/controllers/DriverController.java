
package com.example.brewquest.controllers;


import com.example.brewquest.models.Driver;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.Driver_repository;
import com.example.brewquest.repositories.UserRepository;
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
        User user = usersDao.findById(1L).get();
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
            model.addAttribute("driver", driverToEdit);
        }
        return "users/profile";
    }

    @GetMapping("/driver/{id}/edit")
    public String updateDriver(@ModelAttribute Driver driver) {
        User user = usersDao.findById(driver.getId()).get();
        driver.setUser(user);
        driver.setCarMake(driver.getCarMake());
        driver.setCarModel(driver.getCarModel());
        driver.setLicensePlateNum(driver.getLicensePlateNum());
        driver.setDriversLicenseNum(driver.getDriversLicenseNum());
        return "users/profile";
    }


    @DeleteMapping("/drivers/{id}/delete")
    public String deleteDriver(@PathVariable("id") Long id) {
        driversDao.deleteById(id);
        return "users/profile";
    }
}



