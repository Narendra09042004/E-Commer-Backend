package com.dmart.ecommerce.DTO.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public interface SellerForAdmin
{
//    private Integer getSellerId;
//    private String getName;
//    private String getEmail;
//    private String getPhone;
//    private String getCompany;
//    private LocalDateTime getDate;

     Integer getSellerId();
     String getName();
     String getEmail();
     String getPhone();
     String getCompany();
     LocalDateTime getDate();
}
