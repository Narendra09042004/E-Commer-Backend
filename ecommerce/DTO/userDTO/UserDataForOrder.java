package com.dmart.ecommerce.DTO.userDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataForOrder
{
    private Integer user_id;
    private String name;
}
