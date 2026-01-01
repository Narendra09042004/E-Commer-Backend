package com.dmart.ecommerce.DTO.userDTO.orderData;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderSummaryDTO
{
    private Integer orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String orderStatus;

    private List<UserOrderItemDTO> items;
}
