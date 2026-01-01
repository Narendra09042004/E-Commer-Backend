package com.dmart.ecommerce.Service;

import com.dmart.ecommerce.DTO.ProductDTO.AddProductDTO;
import com.dmart.ecommerce.DTO.ProductDTO.UpdateProductDTO;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithList;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;

public interface ProductService
{
    ApiResponseWithMessage addProduct(AddProductDTO dto);
    ApiResponseWithMessage deleteProductBySeller(Integer productId);
    ApiResponseWithMessage updateProductBySeller(Integer productId, UpdateProductDTO updatedProductDTO);

    ApiResponseWithList getAllProductsForAllUsers();
}
