package com.dmart.ecommerce.controller;

import com.dmart.ecommerce.DTO.OrdersDTO.OrderRequestDTO;
import com.dmart.ecommerce.Service.OrderService;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController
{
    private final OrderService orderService;
    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }


    // place order.
    @PostMapping("/addorder")
    public ResponseEntity<ApiResponseWithMessage> addorder1(@Valid @RequestBody OrderRequestDTO requestDTO) {  return new ResponseEntity<>(orderService.placeorder(requestDTO), HttpStatus.CREATED); }

    // Return Ordered Product.
    @PostMapping("/{orderId}/return-product/{productId}")
    public ResponseEntity<ApiResponseWithMessage> returnProductFromOrder(@PathVariable Integer orderId, @PathVariable Integer productId) {  return ResponseEntity.ok(orderService.returnProductFromOrder(orderId, productId));}

    @PutMapping("/order/cancel/{orderId}")
    public ResponseEntity<ApiResponseWithMessage> cancelOrder(@PathVariable Integer orderId) {  return ResponseEntity.ok(orderService.cancelOrder(orderId));  }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseWithMessage> deleteOrder(@PathVariable Integer id) {   return ResponseEntity.ok(orderService.deleteOrderIfEligible(id));  }
}
