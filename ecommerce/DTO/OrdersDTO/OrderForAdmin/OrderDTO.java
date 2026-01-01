package com.dmart.ecommerce.DTO.OrdersDTO.OrderForAdmin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO
{
    private Integer orderId;
    private Integer userId;
    private LocalDateTime orderDate;
    private LocalDateTime placedDate;
    private Double amount;
    private String status;
    private String address;
    private List<OrderItemDTO> items;
}
