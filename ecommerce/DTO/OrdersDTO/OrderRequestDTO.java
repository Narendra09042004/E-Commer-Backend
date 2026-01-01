package com.dmart.ecommerce.DTO.OrdersDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO
{
//    @NotBlank(message = "email Required")
//    private String email;
    @NotEmpty
    private List<@Valid OrderPlaceDTO> products;
}
