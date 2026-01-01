package com.dmart.ecommerce.DTO.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForAdmin
{
    private Integer productId;
    private String name;
    private Double price;
    private Integer quantity;
    private Integer sellerId;
}
