package com.dmart.ecommerce.Service;

import com.dmart.ecommerce.DTO.AdminDTO.*;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithObject;
import com.dmart.ecommerce.config.CustomUserDetails;

public interface AdminService
{
    ApiResponseWithMessage add(AdminRegistration adminRegistration);

    ApiResponseWithObject updateAdmin(AdminUpdateDto dto, CustomUserDetails currentUser);
    ApiResponseWithObject deleteSellerByAdmin(Integer sellerId);
    ApiResponseWithObject deleteAdmin(Integer id);
    ApiResponseWithObject getProductForAdmin();
    ApiResponseWithObject getAllSellersView();
    ApiResponseWithObject getAllOrderDTOs();
    ApiResponseWithObject getCancelledOrders();
    ApiResponseWithObject getReturnedOrders();
    ApiResponseWithObject userForAdmin();

}
