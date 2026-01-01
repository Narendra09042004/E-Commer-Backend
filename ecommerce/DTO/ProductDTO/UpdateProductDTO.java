package com.dmart.ecommerce.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDTO
{
    private String name;
    private Double price;
    private Integer quantity;
}
