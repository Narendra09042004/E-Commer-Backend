package com.dmart.ecommerce.DTO.userDTO;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClintProfile extends ApiResponseWithMessage {
    private String name;
    private String email;
    private String number;
    private String address;
}
