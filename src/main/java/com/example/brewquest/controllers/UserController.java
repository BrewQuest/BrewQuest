package com.example.brewquest.controllers;


import com.example.brewquest.models.*;
import com.example.brewquest.repositories.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;

    private final DriverRepository driverDao;

    private final FavoriteRepository favoriteDao;

    private final WishlistRepository wishlistDao;

    private final FriendsRepository friendsDao;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, DriverRepository driverDao, FavoriteRepository favoriteDao, WishlistRepository wishlistDao, FriendsRepository friendsDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.driverDao = driverDao;
        this.favoriteDao = favoriteDao;
        this.wishlistDao = wishlistDao;
        this.friendsDao = friendsDao;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    @GetMapping("/aboutus")
    public String aboutUs() {
        return "aboutUs";
    }

//    @PostMapping("/sign-up")
//    public String saveUser(@ModelAttribute User user){
//        String hash = passwordEncoder.encode(user.getPassword());
//        user.setPassword(hash);
//        userDao.save(user);
//        return "redirect:/login";
//    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/sign-up";
        } else {
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            userDao.save(user);
            return "redirect:/login";
        }

        // Save the user and perform further processing
        // ...

    }

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        User user = userDao.findById(id).get();
        Driver driver = driverDao.findByUser(user);
        Friend friendYes = null;
        String friendCheck = "false";
        String friendNo = "";
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user == loggedInUser) {

            if(driver.getUser() != loggedInUser) {
                driver = null;
            }
        }
        System.out.println(loggedInUser.getId());
        List<Friend> userFriends = friendsDao.findByUser(loggedInUser);

        for(Friend friend : userFriends) {
            System.out.println("hasFriends");
            if(friend.getFriend() == user) {
                friendYes = friend;
                friendCheck = "true";
            } else {
                friendCheck = "false";
            }
        }
        List<Favorite> favorites = favoriteDao.findByUser(user);
        List<Wishlist> wishlists = wishlistDao.findByUser(user);
        List<String> favId = new ArrayList<>();
        List<String> wishId = new ArrayList<>();
        for(Favorite favorite : favorites) {
            favId.add(favorite.getBreweryId());
        }
        for(Wishlist wishlist : wishlists) {
            wishId.add(wishlist.getBreweryId());
        }
        String ids = "";
        String wishids = "";
        for(String fav : favId) {
            ids += fav + ",";
        }
        for(String wish : wishId) {
            wishids += wish + ",";
        }


        System.out.println(wishids);
        if(wishids != "") {
            String modifiedWishIds = wishids.substring(0, wishids.length() - 1);
            System.out.println(modifiedWishIds);

            try {
                // Create the URL object with the API endpoint
                URL url = new URL("https://api.openbrewerydb.org/v1/breweries?by_ids=" + modifiedWishIds);

                // Create the HttpURLConnection object
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Set the "Accept" header to request JSON response
                connection.setRequestProperty("Accept", "application/json");

                // Get the response code
                int responseCode = connection.getResponseCode();


                // If the response code indicates success, read the response
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the JSON response
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Map<String, Object>> wishBrews = objectMapper.readValue(response.toString(), new TypeReference<List<Map<String, Object>>>() {
                    });

                    // Add the breweries to the model
                    model.addAttribute("wishlists", wishBrews);

                } else {
                    System.out.println("API request failed with response code: " + responseCode);
                }

                // Disconnect the connection
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(ids != "") {

            String modifiedIds = ids.substring(0, ids.length() - 1);
            System.out.println(modifiedIds);

            try {
                // Create the URL object with the API endpoint
                URL url = new URL("https://api.openbrewerydb.org/v1/breweries?by_ids=" + modifiedIds);

                // Create the HttpURLConnection object
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Set the "Accept" header to request JSON response
                connection.setRequestProperty("Accept", "application/json");

                // Get the response code
                int responseCode = connection.getResponseCode();


                // If the response code indicates success, read the response
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the JSON response
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Map<String, Object>> favBrews = objectMapper.readValue(response.toString(), new TypeReference<List<Map<String, Object>>>() {
                    });

                    // Add the breweries to the model
                    model.addAttribute("favorites", favBrews);

                } else {
                    System.out.println("API request failed with response code: " + responseCode);
                }

                // Disconnect the connection
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("friendNo", friendNo);
        model.addAttribute("friend", friendYes);
        model.addAttribute("friendCheck", friendCheck);
        model.addAttribute("user", user);
        model.addAttribute("user", user);
        model.addAttribute("driver", driver);
        return "users/profile";
    }

    @GetMapping("/profile/{id}/edit")
    public String showEditProfile(@PathVariable Long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user.getId() == userDao.findById(id).get().getId()) {
            User userProfile = userDao.findById(id).get();
            model.addAttribute("user", userProfile);
            return "users/edit";
        } else {
            return "redirect:/home";
        }

    }

    @PostMapping("/profile/{id}/edit")
    public String editProfile(@PathVariable Long id, @ModelAttribute User user){
        User editUser = userDao.findById(id).get();
        editUser.setFirstName(user.getFirstName());
        editUser.setLastName(user.getLastName());
        editUser.setEmail(user.getEmail());
        editUser.setZipcode(user.getZipcode());
        editUser.setUsername(user.getUsername());
        editUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.save(editUser);
        return "redirect:/profile/" + id;
    }

    @PostMapping("/profile/{id}/delete")
    public String deleteProfile(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Driver> drivers = driverDao.findAll();
        for(Driver driver : drivers) {
            if(driver.getUser().getId() == user.getId()) {
                driverDao.delete(driver);
            }
        }

        if(user.getId() == userDao.findById(id).get().getId()) {
            userDao.deleteById(id);
        }

        return "redirect:/home";
    }

    @GetMapping("/leaderboard")
    public String viewLeaderboard(Model model) {
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        users.sort(Comparator.comparing(User::getTotalBreweries).reversed());
        List<Driver> drivers = driverDao.findAll();
        model.addAttribute("drivers", drivers);
        drivers.sort(Comparator.comparing(Driver::getTotalPassengers).reversed());

        return "leaderboard";
    }


}