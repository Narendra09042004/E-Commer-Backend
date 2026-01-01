package com.dmart.ecommerce.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseWithObject
{
    private boolean success;
    private HttpStatus status;
    private Object data;
}
