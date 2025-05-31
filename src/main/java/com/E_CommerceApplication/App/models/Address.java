package com.E_CommerceApplication.App.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    @Size(min = 2, message = "Street name must be larger than 3 characters")
    private String street;

    @NotBlank
    @Size(min = 6, message = "BuildingName must contain 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 2, message = "City name must contain 2 characters")
    private String city;

    @NotBlank
    @Size(min = 2, message = "State name must contain 2 characters")
    private String state;

    @NotBlank
    @Size(min = 2, message = "Country name must contian 2 characters")
    private String country;

    @NotBlank
    @Size(min = 6,message = "Pincode must contain 6 characters")
    private String pincode;

    @OneToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String country, String street, String buildingName, String city, String state, String pincode) {
        this.country = country;
        this.street = street;
        this.buildingName= buildingName;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }
}
