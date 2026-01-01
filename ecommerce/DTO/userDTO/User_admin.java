package com.dmart.ecommerce.DTO.userDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User_admin
{
    private Integer id;
    private String name;
    private String email;
    private String address;
    private String role;
}
