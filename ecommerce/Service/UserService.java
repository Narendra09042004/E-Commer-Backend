package com.dmart.ecommerce.Service;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithList;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithObject;
import com.dmart.ecommerce.DTO.userDTO.Update_user;
import com.dmart.ecommerce.DTO.userDTO.UserRegistration;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.config.CustomUserDetails;

public interface UserService
{
    ApiResponseWithMessage add_user(UserRegistration userRegistration);
    ApiResponseWithMessage update_data(Update_user updateUser);
    ApiResponseWithMessage deleteUser();

    ApiResponseWithObject get_clint_data(CustomUserDetails customUserDetails);
    ApiResponseWithObject getOrderBill(Integer orderId);

    ApiResponseWithList getOrdersByUser(CustomUserDetails curruntUser);
}
