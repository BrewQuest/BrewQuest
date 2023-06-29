package com.example.brewquest.controllers;



import com.example.brewquest.models.*;
import com.example.brewquest.repositories.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.brewquest.models.Driver;
import com.example.brewquest.models.Friend;
import com.example.brewquest.models.User;
import com.example.brewquest.repositories.DriverRepository;
import com.example.brewquest.repositories.FriendsRepository;
import com.example.brewquest.repositories.UserRepository;
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

import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;
private final FriendsRepository friendsDao;
    private final DriverRepository driverDao;

    private final FavoriteRepository favoriteDao;

    private final WishlistRepository wishlistDao;

    private final FriendsRepository friendsDao;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, DriverRepository driverDao, FavoriteRepository favoriteDao, WishlistRepository wishlistDao, FriendsRepository friendsDao) {

        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.friendsDao = friendsDao;
        this.driverDao = driverDao;
        this.favoriteDao = favoriteDao;
        this.wishlistDao = wishlistDao;

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
        User user = userDao.findById(id).orElse(null);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Driver driver = driverDao.findByUser(user);
        Friend friend = friendsDao.findByUserAndFriend(loggedInUser, user);
        model.addAttribute("friend", friend);
        model.addAttribute("driver", driver);
        model.addAttribute("user", user);
        boolean isMyProfile = loggedInUser.getId().equals(user.getId());
        boolean isFriend = friend != null;
        model.addAttribute("isMyProfile", isMyProfile);
        model.addAttribute("isFriend", isFriend);

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


        String modifiedIds = ids.substring(0, ids.length() - 1);
        System.out.println(modifiedIds);
        try {
            // Create the URL object with the API endpoint
            URL url = new URL("https://api.openbrewerydb.org/v1/breweries?by_ids=" + wishids);

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
                List<Map<String, Object>> wishBrews = objectMapper.readValue(response.toString(), new TypeReference<List<Map<String, Object>>>() {});


        model.addAttribute("friend", friend);
        model.addAttribute("driver", driver);
        model.addAttribute("user", user);

        boolean isMyProfile = loggedInUser.getId().equals(user.getId());
        
        boolean isFriend = friend != null;

        model.addAttribute("isMyProfile", isMyProfile);
        model.addAttribute("isFriend", isFriend);

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
