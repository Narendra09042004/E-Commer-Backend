package com.dmart.ecommerce.DTO.ProductDTO;

import com.dmart.ecommerce.Entity.Product;
import com.dmart.ecommerce.Entity.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductDTO
{
    private Seller seller_id;
    private Product product_id;
}
