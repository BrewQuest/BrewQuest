package com.example.brewquest.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reviews")

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int breweryId;
    @Column(nullable = false)
    private int rating;
    @Column(columnDefinition = "text")
    private String description;
    @Column(nullable = false, length = 50)
    private String date;
    @Column
    private int passengers;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;
}
