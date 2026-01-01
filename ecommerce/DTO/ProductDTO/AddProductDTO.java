package com.dmart.ecommerce.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDTO
{
    private String name;
    private Double price;
    private Integer quantity;
    //private Integer sellerid;
}
