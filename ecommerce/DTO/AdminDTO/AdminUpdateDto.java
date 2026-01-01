package com.dmart.ecommerce.DTO.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateDto
{
    private String name;
    private String email;
    private String phone;
}
