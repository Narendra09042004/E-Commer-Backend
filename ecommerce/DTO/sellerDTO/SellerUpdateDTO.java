package com.dmart.ecommerce.DTO.sellerDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SellerUpdateDTO
{
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    private String phone;

    private String company;
}
