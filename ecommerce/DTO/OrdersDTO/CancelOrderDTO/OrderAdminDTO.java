package com.dmart.ecommerce.DTO.OrdersDTO.CancelOrderDTO;

import com.dmart.ecommerce.DTO.OrdersDTO.OrderForAdmin.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAdminDTO
{
    private Integer orderId;
    private Integer userId;
    private Double amount;
    private String status;
    private LocalDateTime orderDate;
    private List<OrderItemDTO> items;
}
