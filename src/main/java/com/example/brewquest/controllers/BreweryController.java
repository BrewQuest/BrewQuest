package com.example.brewquest.controllers;

import com.example.brewquest.repositories.UserRepository;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.http.HttpRequest;

@Controller
public class BreweryController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;

    public BreweryController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
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
                        breweryName = jsonObject.getString("name");
                        breweryState = jsonObject.getString("state_province");
                        breweryCity = jsonObject.getString("city");
                        breweryStreet = jsonObject.getString("street");
                        breweryWebsite = jsonObject.getString("website_url");
                        breweryCountry = jsonObject.getString("country");
                        breweryZipcode = jsonObject.getString("postal_code");


                        model.addAttribute("name", breweryName);
                        model.addAttribute("state", breweryState);
                        model.addAttribute("street", breweryStreet);
                        model.addAttribute("city",breweryCity);
                        model.addAttribute("zipcode",breweryZipcode);
                        model.addAttribute("country",breweryCountry);
                        model.addAttribute("website",breweryWebsite);
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
        return "landingpage";
    }
}
