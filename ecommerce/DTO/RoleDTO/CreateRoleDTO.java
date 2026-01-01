package com.dmart.ecommerce.DTO.RoleDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleDTO
{
    @NotBlank(message = "RoleName Required")
    private String name;
}
