package com.dmart.ecommerce.DTO.OrdersDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlaceDTO // this show the item to place.
{
    @NotNull(message = "Product ID is required")
    private Integer productid;

    @Min(value = 1,message = "quantity must be greater than 1")
    private Integer quantity;
}
