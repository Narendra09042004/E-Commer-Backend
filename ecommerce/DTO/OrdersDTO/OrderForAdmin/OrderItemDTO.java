package com.dmart.ecommerce.DTO.OrdersDTO.OrderForAdmin;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDTO
{
    private Integer productId;
    private Integer quantity;
    private Double price;
}
