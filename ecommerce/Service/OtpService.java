package com.dmart.ecommerce.Service;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;

public interface OtpService
{
    ApiResponseWithMessage sendOtp(String mobileNumber);
}
