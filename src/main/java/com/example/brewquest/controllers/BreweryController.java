package com.example.brewquest.controllers;

import com.example.brewquest.models.Favorite;
import com.example.brewquest.models.User;
import com.example.brewquest.models.Wishlist;
import com.example.brewquest.repositories.FavoriteRepository;
import com.example.brewquest.repositories.UserRepository;
import com.example.brewquest.repositories.WishlistRepository;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BreweryController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;

    private final FavoriteRepository favoriteDao;
    private final WishlistRepository wishlistDao;

    public BreweryController(UserRepository userDao, PasswordEncoder passwordEncoder, FavoriteRepository favoriteDao, WishlistRepository wishlistDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.favoriteDao = favoriteDao;
        this.wishlistDao = wishlistDao;
    }
    //save brewery to users wishlist
    @PostMapping("/brewery/{id}/wishlist")
    public String saveBrewery(@PathVariable String id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setBreweryId(id);
        wishlistDao.save(wishlist);
        return "redirect:/brewery/" + id;
    }

    //save brewery to users favorites
    @PostMapping("/brewery/{id}/favorite")
    public String favBrewery(@PathVariable String id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setBreweryId(id);
        favoriteDao.save(favorite);
        return "redirect:/brewery/" + id;
    }

    @PostMapping("/deletewishlist/{id}")
    public String deleteWishlist(@PathVariable String id) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Wishlist> wishlists = wishlistDao.findByUser(user);
        for( Wishlist wishlist : wishlists) {
            if(wishlist.getBreweryId().equals(id)) {
                wishlistDao.delete(wishlist);
            }
        }
        return "redirect:/profile/" + user.getId();
    }

    @PostMapping("/deletefavorite/{id}")
    public String deletefavorite(@PathVariable String id) {
        System.out.println(id);
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Favorite> favorites = favoriteDao.findByUser(user);
        for( Favorite favorite : favorites) {
            System.out.println(favorite.getBreweryId());
            System.out.println(id);
            if(favorite.getBreweryId().equals(id)) {
                favoriteDao.delete(favorite);
            }
        }
        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("/brewery/{id}")
    public String viewBrewery(@PathVariable String id, Model model) {
        try {
            // Create the URL object with the API endpoint
            URL url = new URL("https://api.openbrewerydb.org/v1/breweries?by_ids=" + id);

            // Create the HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Set the "Accept" header to request JSON response
            connection.setRequestProperty("Accept", "application/json");

            // Get the response code
            int responseCode = connection.getResponseCode();

            String breweryName = null;
            String breweryState = null;
            String breweryStreet = null;
            String breweryCity = null;
            String breweryZipcode = null;
            String breweryCountry = null;
            String breweryId = null;
            String breweryWebsite = null;



            // If the response code indicates success (200), read the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Extract and print the name
                String jsonResponse = response.toString();
                if (!jsonResponse.isEmpty()) {
                    // Parse the JSON response
                    JSONArray jsonArray = new JSONArray(jsonResponse);
                    if (jsonArray.length() > 0) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        breweryName = jsonObject.optString("name", "");
                        breweryState = jsonObject.optString("state_province", "");
                        breweryCity = jsonObject.optString("city");
                        breweryStreet = jsonObject.optString("street", "");
                        breweryCountry = jsonObject.optString("country");
                        breweryZipcode = jsonObject.optString("postal_code", "");
                        breweryId = jsonObject.getString("id");
                        breweryWebsite = jsonObject.optString("website_url", "");


                        model.addAttribute("name", breweryName);
                        model.addAttribute("state", breweryState);
                        model.addAttribute("street", breweryStreet);
                        model.addAttribute("city",breweryCity);
                        model.addAttribute("zipcode",breweryZipcode);
                        model.addAttribute("country",breweryCountry);
                        model.addAttribute("website",breweryWebsite);
                        model.addAttribute("id",breweryId);
                    } else {
                        System.out.println("No data found.");
                    }
                }
            } else {
                System.out.println("API request failed with response code: " + responseCode);
            }

            // Disconnect the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "brewery";
    }
}
