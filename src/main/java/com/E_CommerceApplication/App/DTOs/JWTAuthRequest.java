package com.E_CommerceApplication.App.DTOs;

import lombok.Data;

@Data
public class JWTAuthRequest {
    private String UserName;
    private String password;
}
