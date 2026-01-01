package com.dmart.ecommerce.DTO.OrdersDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO1
{
    private String productName;
    private Integer quantity;
}