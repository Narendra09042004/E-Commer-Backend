package com.dmart.ecommerce.Service.ServiceImpls;

import com.dmart.ecommerce.Exception.IllegalArgumentException;
import com.dmart.ecommerce.Exception.ResourceAlreadyExistsException;
import com.dmart.ecommerce.Exception.ResourceNotFoundException;
import com.dmart.ecommerce.Utils.AppUtils;
import com.dmart.ecommerce.DTO.ProductDTO.AddProductDTO;
import com.dmart.ecommerce.DTO.ProductDTO.UpdateProductDTO;
import com.dmart.ecommerce.DTO.ProductDTO.ProductListForAll;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithList;
import com.dmart.ecommerce.Entity.Product;
import com.dmart.ecommerce.Entity.*;
import com.dmart.ecommerce.Service.ProductService;
import com.dmart.ecommerce.Repository.ProductRepository;
import com.dmart.ecommerce.Repository.SellerRepository;
import com.dmart.ecommerce.Repository.UserRepository;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final SellerRepository sellerRepository;


    // add new product using seller id(it means the seller will add the product of his company).
    @Transactional
    @Override
    public ApiResponseWithMessage addProduct(AddProductDTO dto) {
        if (dto.getPrice() <= 0 || dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Price and Quantity must be > 0");
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Seller seller = sellerRepository.findByEmail(email);
        if(seller == null)
        {
            throw new IllegalArgumentException("Seller Not Found At email:"+ email);
        }

//        Optional<Product> existingProduct = productRepository.findByNameAndSeller_SellerId(dto.getName(), seller.getSellerId());
        Product existingProduct = productRepository.findByNameAndSeller_SellerId(dto.getName(),seller.getSellerId());

        if (existingProduct != null)
        {
            throw new ResourceAlreadyExistsException("A product with this name already exists for this seller.");
        }

        Product product = AppUtils.toProduct(dto, seller);
        productRepository.save(product);

        return new ApiResponseWithMessage(true, HttpStatus.OK, "Product added successfully");
    }


    // delete product.
    @Transactional
    @Override
    public ApiResponseWithMessage deleteProductBySeller(Integer productId)
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Seller seller = sellerRepository.findByEmail(email);

        Product product = productRepository.findByProductIdAndSeller_SellerId(productId, seller.getSellerId());
//                .orElseThrow(() -> new ResourceNotFoundException("Product not found or does not belong to the seller."));
        if(product == null)
        {
            throw new ResourceNotFoundException("Product not found or does not belong to the seller.");
        }

        productRepository.delete(product);

        return new ApiResponseWithMessage(true, HttpStatus.OK, "Product deleted successfully");
    }

    // update product.
    @Override
    public ApiResponseWithMessage updateProductBySeller(Integer productId, UpdateProductDTO updateProductDTO)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sellerEmail = authentication.getName();

        Seller seller = sellerRepository.findByEmail(sellerEmail);

        Product product = productRepository.findByProductIdAndSeller_SellerId(productId, seller.getSellerId());
//                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found Under This Seller"));
        if(product == null)
        {
            throw new ResourceNotFoundException("Product Not found under this seller");
        }

        // Validate individual fields
        if (updateProductDTO.getPrice() != null && updateProductDTO.getPrice() <= 0)
        {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        if (updateProductDTO.getQuantity() != null && updateProductDTO.getQuantity() <= 0)
        {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // Update only the provided fields
        if (updateProductDTO.getName() != null)
        {
            product.setName(updateProductDTO.getName());
        }

        if (updateProductDTO.getPrice() != null)
        {
            product.setPrice(updateProductDTO.getPrice());
        }

        if (updateProductDTO.getQuantity() != null)
        {
            product.setQuantity(updateProductDTO.getQuantity());
        }
        productRepository.save(product);

        return new ApiResponseWithMessage(true, HttpStatus.OK,"Product Updated Successfully");
    }


    // List of Products
    @Override
    public ApiResponseWithList getAllProductsForAllUsers()
    {
        List<Product> products = productRepository.findAll();

        if(products.isEmpty())
        {
            throw new ResourceNotFoundException("No Products Avilable.");
        }
        List<ProductListForAll> list = AppUtils.mapProductsForAllUser(products);

        return new ApiResponseWithList(true,HttpStatus.OK,list);
    }
}



