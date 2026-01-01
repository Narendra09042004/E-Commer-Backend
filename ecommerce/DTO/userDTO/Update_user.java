package com.dmart.ecommerce.DTO.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Update_user
{
    @Email(message = "Email should be valid")
    private String email;

    private String password;

    //@Pattern(regexp = "^(\\+\\d{1,3}[ -]?)?\\d{10}$", message = "Phone must be 10 digits, optionally with country code")
    private String number;

    private String address;
}
