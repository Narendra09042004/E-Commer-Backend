package com.dmart.ecommerce.controller;

import com.dmart.ecommerce.DTO.RequestDTO.AuthRequest;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.DTO.ResponseDTO.AuthResponse;
import com.dmart.ecommerce.Service.AuthService;
import com.dmart.ecommerce.Service.OtpService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService authService;
    @Autowired
    private OtpService otpService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest dto)
    {
        return ResponseEntity.ok(authService.login(dto));
    }

    // this api is used to send OTP in the mobile number.
    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponseWithMessage> sendOtp(@RequestParam String phone)
    {
        ApiResponseWithMessage response = otpService.sendOtp(phone);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponseWithMessage> sendOtp(@RequestParam String email, HttpSession session)
    {
        ApiResponseWithMessage response = authService.handleForgotPassword(email, session);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // reset password.
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseWithMessage> resetPassword(@RequestParam String otp, @RequestParam String newPassword,HttpSession session)
    {
        return ResponseEntity.ok(authService.resetPassword(otp, newPassword,session));
    }
}