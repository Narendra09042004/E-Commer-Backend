package com.dmart.ecommerce.config.Jwt;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, java.io.IOException
    {

        System.out.println("UNAUTHORIZED REQUEST - Reason: " + authException.getMessage());

        ApiResponseWithMessage apiResponse = new ApiResponseWithMessage(
                false,
                HttpStatus.UNAUTHORIZED,
                "Unauthorized: You cannot access this API"
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(json);
    }
}
