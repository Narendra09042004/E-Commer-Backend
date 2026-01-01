package com.dmart.ecommerce.DTO.sellerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class productForSeller {
    private Integer productid;

    private String  name;

    private Double price;

    private Integer quantity;
}
