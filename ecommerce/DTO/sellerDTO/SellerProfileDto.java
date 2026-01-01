package com.dmart.ecommerce.DTO.sellerDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerProfileDto
{
    private Integer sellerid;

    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    private String company;

    private LocalDateTime createdDate=LocalDateTime.now();
}
