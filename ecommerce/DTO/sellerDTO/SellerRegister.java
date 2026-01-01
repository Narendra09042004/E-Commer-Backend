package com.dmart.ecommerce.DTO.sellerDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerRegister
{
    @NotBlank(message = "Name must not be null")
    private String name;

    @NotBlank(message = "Email must not be null")
    @Email(message = "Must be a valid email address")
    private String email;

    @NotBlank(message = "Password must not be null")
    private String password;

    @NotBlank(message = "Phone must not be null")
    private String phone;

    @NotBlank(message = "Company must not be null")
    private String company;
    private String sellerkey;
}
