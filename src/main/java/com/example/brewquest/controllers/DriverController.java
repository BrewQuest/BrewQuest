
package com.example.brewquest.controllers;


import com.example.brewquest.models.Driver;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.Driver_repository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        User user = usersDao.findById(1L).get();
        driver.setUser(user);
        driver.setCarMake(driver.getCarMake());
        driver.setCarModel(driver.getCarModel());
        driver.setLicensePlateNum(driver.getLicensePlateNum());
        driver.setDriversLicenseNum(driver.getDriversLicenseNum());
    }
}

@GetMapping("/driver/{id}/delete")

//@PostMapping("/posts/{id}/delete")
//    public String deletePost (@PathVariable long id){
//        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Post postToDelete = postsDao.findById(id).get();
//
//        if(loggedInUser.getId() == postToDelete.getUser().getId()) {
//            postsDao.deleteById(id);
//
//        }
//        return "redirect:/posts";
//    @PostMapping("/posts/{id}/edit")
//    public String updatePost(@ModelAttribute Post newPost) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        newPost.setUser(user);
//        postsDao.save(newPost);
//        return "redirect:/posts";
//    }
    }

