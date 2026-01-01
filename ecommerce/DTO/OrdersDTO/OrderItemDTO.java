package com.dmart.ecommerce.DTO.OrdersDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO
{
    private Integer orderId;
    private Double totalAmount;
    private String status;
    private Integer Quantity;
    private List<OrderResponseDTO1> items;
}
