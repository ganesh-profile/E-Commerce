package com.E_CommerceApplication.App.DTOs;

import lombok.Data;

@Data
public class JWTAuthResponse {
    private String token;
    private UserDTO user;
}
