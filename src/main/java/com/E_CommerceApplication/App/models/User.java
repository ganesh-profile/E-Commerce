package com.E_CommerceApplication.App.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Size(min = 4,max = 35, message = "first name must be between 4 and 35")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "First name must not contain numbers or special characters")
    private String firstName;

    @Size(min = 4, max = 35, message = "Last name should be between 4 and 35")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name should not contain numbers or special characters")
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Size(min = 10, max = 10, message = "Mobile Number should exactly contain 10 numbers")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile Number must contain only numbers")
    private int mobileNumber;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinTable(name = "user_address",joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private Cart cart;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



}
