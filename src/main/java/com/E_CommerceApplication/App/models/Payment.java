package com.E_CommerceApplication.App.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payment_Id;

    @OneToOne(mappedBy = "payment", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Order order;

    @NotBlank
    @Size(min = 4, message = "Payment method must contain 4 characters")
    private String paymentMethod;


}
