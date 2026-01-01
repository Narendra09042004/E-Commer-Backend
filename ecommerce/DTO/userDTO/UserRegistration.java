package com.dmart.ecommerce.DTO.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration
{
    @NotBlank(message = "Name must not be null")
    private String name;

    @NotBlank(message = "Please Enter Email")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Password must not be null")
    private String password;

    @NotBlank(message = "Number must not be null")
    private String number;

    @NotBlank(message = "Address must not be null")
    private String address;
}
