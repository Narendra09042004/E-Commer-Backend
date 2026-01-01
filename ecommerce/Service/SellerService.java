package com.dmart.ecommerce.Service;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithObject;
import com.dmart.ecommerce.DTO.sellerDTO.SellerUpdateDTO;
import com.dmart.ecommerce.DTO.sellerDTO.SellerRegister;

public interface SellerService
{
    ApiResponseWithObject getProductsResponseBySeller();
    ApiResponseWithObject getSellerProfile();

    ApiResponseWithMessage add(SellerRegister sellerRegister);
    ApiResponseWithMessage updateSellerDetails(SellerUpdateDTO dto);

}
