package com.dmart.ecommerce.DTO.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserForAdmin
{
    private Integer id;

    private String name;

    private String email;

    private String number;

    private String address;
}
