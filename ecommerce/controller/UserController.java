package com.dmart.ecommerce.controller;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithList;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithObject;
import com.dmart.ecommerce.DTO.userDTO.*;
import com.dmart.ecommerce.config.CustomUserDetails;
import com.dmart.ecommerce.Service.UserService;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/user")
public class UserController
{
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    //User Registration
    @PostMapping("/user-registration")
    public ResponseEntity<ApiResponseWithMessage> user_registration(@Valid @RequestBody UserRegistration userRegistration) {
        return new ResponseEntity<>(userService.add_user(userRegistration), HttpStatus.CREATED);
    }

    // Get Single User Data.
    @GetMapping("/user-data")
    public ResponseEntity<ApiResponseWithObject> get_data_for_clint(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(userService.get_clint_data(customUserDetails));
    }

    // Update User
    @PutMapping("/update-user")
    public ResponseEntity<ApiResponseWithMessage> update_user(@Valid @RequestBody Update_user updateUser) {
        return ResponseEntity.ok(userService.update_data(updateUser));
    }

    // delete user.
    @DeleteMapping("/Delete-User")
    public ResponseEntity<ApiResponseWithMessage> deleteUser() {
        return ResponseEntity.ok(userService.deleteUser());
    }

    // Get bill
    @GetMapping("/user/order-bill/{orderId}")
    public ResponseEntity<ApiResponseWithObject> getOrderBill(@PathVariable Integer orderId) {
        return ResponseEntity.ok(userService.getOrderBill(orderId));
    }

    // get all order bill of a single user.
    @GetMapping("/user/user-orders")
    public ResponseEntity<ApiResponseWithList> getUserOrders(@AuthenticationPrincipal CustomUserDetails curruntUser) {
        return ResponseEntity.ok(userService.getOrdersByUser(curruntUser));
    }
}
