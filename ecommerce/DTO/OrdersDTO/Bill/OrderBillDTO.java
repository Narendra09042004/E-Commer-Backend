package com.dmart.ecommerce.DTO.OrdersDTO.Bill;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBillDTO extends ApiResponseWithMessage
{
    // order data.
    private Integer orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    // user data.
    private Integer userId;
    private String userName;
    private String userAddress;

    private List<BillItemDTO> items;
}
