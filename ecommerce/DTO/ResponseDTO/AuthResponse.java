package com.dmart.ecommerce.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse
{
    private boolean success;
    private HttpStatus status;
    private String message;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String token;
}
