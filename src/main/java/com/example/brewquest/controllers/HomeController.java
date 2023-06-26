package com.example.brewquest.controllers;

import com.example.brewquest.models.User;
import com.example.brewquest.repositories.Driver_repository;
import com.example.brewquest.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;

    private final Driver_repository driverDao;

    public HomeController(UserRepository userDao, PasswordEncoder passwordEncoder, Driver_repository driverDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.driverDao = driverDao;
    }


    @GetMapping("/")
    public String home() {
        return "landingpage";
    }

    @GetMapping("/home")
        public String homePage(Model model){

        try {
            // Create the URL object with the API endpoint
            URL url = new URL("https://api.openbrewerydb.org/v1/breweries/random?size=10");

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
                List<Map<String, Object>> breweries = objectMapper.readValue(response.toString(), new TypeReference<List<Map<String, Object>>>() {});

                // Add the breweries to the model
                model.addAttribute("breweries", breweries);

            } else {
                System.out.println("API request failed with response code: " + responseCode);
            }

            // Disconnect the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
            return "HomePage";
    }
}
