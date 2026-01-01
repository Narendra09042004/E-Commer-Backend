package com.dmart.ecommerce.DTO.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO
{
    @NotBlank(message = "email Required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "password Required")
    private String password;
}
