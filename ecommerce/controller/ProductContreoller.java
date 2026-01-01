package com.dmart.ecommerce.controller;

import com.dmart.ecommerce.DTO.ProductDTO.AddProductDTO;
import com.dmart.ecommerce.DTO.ProductDTO.UpdateProductDTO;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithList;
import com.dmart.ecommerce.Service.ProductService;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductContreoller
{
    private final ProductService productService;
    public ProductContreoller(ProductService productService)
    {
        this.productService = productService;
    }


    // add new product.
    @PostMapping("/add")
    public ResponseEntity<ApiResponseWithMessage> addProduct(@Valid @RequestBody AddProductDTO dto) {  return new ResponseEntity<>(productService.addProduct(dto), HttpStatus.CREATED);  }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponseWithMessage> deleteProductBySeller(@Valid @PathVariable Integer productId) { return ResponseEntity.ok(productService.deleteProductBySeller(productId)); }

    // update product.
    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponseWithMessage> updateProductBySeller(@Valid @PathVariable Integer productId, @RequestBody UpdateProductDTO updatedProductDTO) {  return ResponseEntity.ok(productService.updateProductBySeller(productId,updatedProductDTO)); }

    @GetMapping("/products/all")
    public ResponseEntity<ApiResponseWithList> getAllProductsForAll() {  return ResponseEntity.ok(productService.getAllProductsForAllUsers());  }
}
