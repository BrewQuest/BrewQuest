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
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String driversLicenseNum;
    @Column(nullable = false, length = 50)
    private String licensePlateNum;
    @Column(nullable = false, length = 50)
    private String carMake;
    @Column(nullable = false, length = 50)
    private String carModel;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int totalPassengers;

    @OneToOne
    private User user;

    public Driver(String driversLicenseNum, String licensePlateNum, String carMake, String carModel) {
        this.driversLicenseNum = driversLicenseNum;
        this.licensePlateNum = licensePlateNum;
        this.carMake = carMake;
        this.carModel = carModel;
    }
}
