package com.dmart.ecommerce.Service;

import com.dmart.ecommerce.DTO.OrdersDTO.OrderRequestDTO;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;

public interface OrderService
{
    ApiResponseWithMessage placeorder(OrderRequestDTO request_product);
    ApiResponseWithMessage cancelOrder(Integer orderId);
    ApiResponseWithMessage returnProductFromOrder(Integer orderId, Integer productId);
    ApiResponseWithMessage deleteOrderIfEligible(Integer orderId);
}
