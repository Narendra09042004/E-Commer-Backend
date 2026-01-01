package com.dmart.ecommerce.DTO.sellerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerReturnDTO
{
    private Integer orderItemId;
    private Integer productId;
    private String productName;
    private Integer userId;
    private Integer quantity;
    private LocalDateTime returnedAt;
}
