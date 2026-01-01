package com.dmart.ecommerce.Service;

import com.dmart.ecommerce.DTO.RequestDTO.AuthRequest;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.DTO.ResponseDTO.AuthResponse;
import jakarta.servlet.http.HttpSession;

public interface AuthService
{
    AuthResponse login(AuthRequest dto);
    ApiResponseWithMessage handleForgotPassword(String email, HttpSession session);
    ApiResponseWithMessage resetPassword(String otp, String newPassword, HttpSession session);
}
