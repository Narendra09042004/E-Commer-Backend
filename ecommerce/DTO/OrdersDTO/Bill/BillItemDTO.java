package com.dmart.ecommerce.DTO.OrdersDTO.Bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillItemDTO
{
    private String productName;
    private Double pricePerItem;
    private Integer quantity;
    private Double totalPrice;
}
