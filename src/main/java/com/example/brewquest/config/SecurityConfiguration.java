package com.example.brewquest.config;

import com.example.brewquest.services.UserDetailsLoader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        /* Pages that require authentication
                         * only authenticated users can create and edit ads */
                        .requestMatchers("/review/*/edit", "/review/*/delete", "/profile/*/edit", "/profile/*/delete", "profile/*/reviews","/brewery/*/create-review", "/driver/*/edit", "/driver/*/delete", "/addFriends", "/profile/*/friends", "/signup-driver", "/brewery/create-review", "/brewery/{id}/favorite", "/brewery/{id}/wishlist", "/addFriend/*", "/deleteFriend/{id}", "/deletewishlist/*/*").authenticated()
                        /* Pages that do not require authentication
                         * anyone can visit the home page, register, login, and view ads */
                        .requestMatchers("/", "/sign-up", "/login", "profile/*", "/home", "/leaderboard", "/brewery/*", "/search", "/aboutus").permitAll()

         // allow loading of static resources

                        .requestMatchers("/", "/posts", "/posts/*", "/sign-up", "/login", "profile/*", "profile/*/edit").permitAll()

                        // allow loading of static resources

                        .requestMatchers("/css/**", "/js/**", "/img/**", "/keys.js").permitAll()
                )
                /* Login configuration */
                .formLogin((login) -> login.loginPage("/login").defaultSuccessUrl("/home"))
                /* Logout configuration */
                .logout((logout) -> logout.logoutSuccessUrl("/home"))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}