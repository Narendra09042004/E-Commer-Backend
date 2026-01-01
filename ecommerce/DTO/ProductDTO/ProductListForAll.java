package com.dmart.ecommerce.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductListForAll
{
    private Integer Id;
    private String name;
    private Double price;
    private Integer quantity;
}
