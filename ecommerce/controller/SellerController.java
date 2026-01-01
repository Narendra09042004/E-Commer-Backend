package com.dmart.ecommerce.controller;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithObject;
import com.dmart.ecommerce.DTO.sellerDTO.SellerUpdateDTO;
import com.dmart.ecommerce.DTO.sellerDTO.SellerRegister;
import com.dmart.ecommerce.Service.SellerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/seller")
public class SellerController
{
    private final SellerService sellerService;

    public SellerController(SellerService sellerService)
    {
        this.sellerService = sellerService;
    }

    //add/register seller
    @PostMapping("/register")
    public ResponseEntity<ApiResponseWithMessage> addseller(@Valid @RequestBody SellerRegister sellerRegister) {   return new ResponseEntity<>(sellerService.add(sellerRegister), HttpStatus.CREATED);  }

    @GetMapping("/products")
    public ResponseEntity<ApiResponseWithObject> getProductsBySeller() {  return ResponseEntity.ok(sellerService.getProductsResponseBySeller()); }

    @PutMapping("/update-seller")
    public ResponseEntity<ApiResponseWithMessage> updateSeller(@Valid @RequestBody SellerUpdateDTO dto) {  return ResponseEntity.ok(sellerService.updateSellerDetails(dto));  }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponseWithObject> getSellerProfile() {  return ResponseEntity.ok(sellerService.getSellerProfile()); }
}
