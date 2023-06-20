package com.example.brewquest.controllers;

import com.example.brewquest.models.Review;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.ReviewRepository;
import com.example.brewquest.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@GetMapping("/review/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model){
        if (ReviewDaos.findById(id).isPresent()) {
            Review reviewtoEdit = ReviewDaos.findById(id).get();
            model.addAttribute("driver", reviewtoEdit);
        }
        return "/Reviews/Edit-Review";
}

}
// @GetMapping("/driver/{id}/edit")
//    public String showEditForm(@PathVariable long id, Model model) {
//        if (driversDao.findById(id).isPresent()) {
//            Driver driverToEdit = driversDao.findById(id).get();
//            model.addAttribute("driver", driverToEdit);  // Make sure the attribute name is "driver"
//        }
//        return "Driver/edit-driver";
//    }
//
//    @PostMapping("/driver/{id}/edit")
//    public String updateDriver(@PathVariable long id, @ModelAttribute Driver driver) {
//        User user = usersDao.findById(id).get();
//        driver.setUser(user);
//        driver.setCarMake(driver.getCarMake());
//        driver.setCarModel(driver.getCarModel());
//        driver.setLicensePlateNum(driver.getLicensePlateNum());
//        driver.setDriversLicenseNum(driver.getDriversLicenseNum());
//        driversDao.save(driver);
//        return "users/profile";
//    }