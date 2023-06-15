package com.example.brewquest.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, length = 50)
    private String birthDate;
    @Column(nullable = false, unique = true, length = 100)
    private String username;
    @Column(nullable = false, length=100)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    private int totalBreweries;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Review> reviews;

    public User(User copy) {
        id = copy.id;
        lastName = copy.lastName;
        firstName = copy.firstName;
        birthDate = copy.birthDate;
        username = copy.username;
        email = copy.email;
        password = copy.password;
        totalBreweries = copy.totalBreweries;
    }
}
