
package com.example.brewquest.controllers;


import com.example.brewquest.models.Driver;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.DriverRepository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DriverController {
    private final DriverRepository driversDao;
    private final UserRepository usersDao;

    public DriverController(DriverRepository driversDao, UserRepository usersDao) {
        this.driversDao = driversDao;
        this.usersDao = usersDao;
    }


    @GetMapping("/signup-driver")
    public String showSignupForm(Model model) {
        model.addAttribute("driver", new Driver());
        return "Driver/sign-up-driver";
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
        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("/driver/{id}/edit")
    public String showEditForm(Model model, @PathVariable("id") String id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Driver driver = driversDao.findByUser(user);
        if (driver != null) {
            model.addAttribute("driver", driver);
        }
        return "Driver/edit-driver";
    }


    @PostMapping("/driver/{id}/edit")
    public String updateDriver(@PathVariable long id, @ModelAttribute Driver driver) {
       User user = usersDao.findById(id).get();
        Driver editDriver = driversDao.findByUser(user);
        editDriver.setCarMake(driver.getCarMake());
        editDriver.setCarModel(driver.getCarModel());
        editDriver.setLicensePlateNum(driver.getLicensePlateNum());
        editDriver.setDriversLicenseNum(driver.getDriversLicenseNum());
        driversDao.save(editDriver);
        return "redirect:/profile/" + id;
    }

    @PostMapping("/driver/{id}/delete")
    public String deleteDriver(@PathVariable("id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            driversDao.deleteById(id);
        return "redirect:/profile/" + user.getId();
    }
}




