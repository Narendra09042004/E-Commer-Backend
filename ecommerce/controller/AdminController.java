package com.dmart.ecommerce.controller;

import com.dmart.ecommerce.DTO.AdminDTO.*;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithObject;
import com.dmart.ecommerce.config.CustomUserDetails;
import com.dmart.ecommerce.Service.AdminService;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/admin")
public class AdminController
{
    private final AdminService adminService;

    public AdminController(AdminService adminService)
    {
        this.adminService = adminService;
    }

    // add/register admin for ecommerce;
    @PostMapping("/registerAdmin")
    public ResponseEntity<ApiResponseWithMessage> addadmin(@Valid @RequestBody AdminRegistration adminRegistration)
    {   return new ResponseEntity<>(adminService.add(adminRegistration), HttpStatus.CREATED);  }

    //update admin
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/Update-admin")
    public ResponseEntity<ApiResponseWithObject> adminUpdate(@Valid @RequestBody AdminUpdateDto adminUpdateDto, @AuthenticationPrincipal CustomUserDetails currentUser){  return ResponseEntity.ok(adminService.updateAdmin(adminUpdateDto,currentUser));   }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/AllProducts")
    public ResponseEntity<ApiResponseWithObject> adminSelectProduct()
    {
        return ResponseEntity.ok(adminService.getProductForAdmin());
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/AllSellers")
    public ResponseEntity<ApiResponseWithObject> listSellers()
    {
        return  ResponseEntity.ok(adminService.getAllSellersView());
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-seller/{sellerId}")
    public ResponseEntity<ApiResponseWithObject> deleteSeller(@Valid @PathVariable Integer sellerId) { return ResponseEntity.ok(adminService.deleteSellerByAdmin(sellerId)); }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/AllOrders")
    public ResponseEntity<ApiResponseWithObject> getAllOrders() { return ResponseEntity.ok(adminService.getAllOrderDTOs()); }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/cancelled-orders")
    public ResponseEntity<ApiResponseWithObject> getCancelledOrders() { return ResponseEntity.ok(adminService.getCancelledOrders()); }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/returned-orders")
    public ResponseEntity<ApiResponseWithObject> getReturnedOrders() { return ResponseEntity.ok(adminService.getReturnedOrders()); }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/AllUsers")
    public ResponseEntity<ApiResponseWithObject> listUsers() { return ResponseEntity.ok(adminService.userForAdmin()); }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/Delete-Admin/{id}")
    public ResponseEntity<ApiResponseWithObject> deleteAdmin(@Valid @PathVariable Integer id){ return ResponseEntity.ok(adminService.deleteAdmin(id)); }

}
