package com.dmart.ecommerce.DTO.userDTO.orderData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderItemDTO
{
    private String productName;
    private Integer quantity;
    private Double pricePerItem;
    private Double totalItemPrice;
}
