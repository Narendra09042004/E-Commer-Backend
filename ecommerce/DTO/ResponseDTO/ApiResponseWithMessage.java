package com.dmart.ecommerce.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseWithMessage
{
    private boolean success;
    private HttpStatus status;
    private String message;
}
