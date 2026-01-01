package com.dmart.ecommerce.Service.ServiceImpls;

import com.dmart.ecommerce.Exception.ResourceAlreadyExistsException;
import com.dmart.ecommerce.Exception.ResourceIncorrectException;
import com.dmart.ecommerce.Exception.ResourceNotFoundException;
import com.dmart.ecommerce.Utils.AppUtils;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithObject;
import com.dmart.ecommerce.DTO.sellerDTO.SellerUpdateDTO;
import com.dmart.ecommerce.DTO.sellerDTO.SellerProfileDto;
import com.dmart.ecommerce.DTO.sellerDTO.SellerRegister;
import com.dmart.ecommerce.Entity.*;
import com.dmart.ecommerce.Repository.RoleRepository;
import com.dmart.ecommerce.Service.SellerService;
import com.dmart.ecommerce.Repository.ProductRepository;
import com.dmart.ecommerce.Repository.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService
{

    private final SellerRepository sellerRepository;

    private final ProductRepository productRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final static String sellerkey="SELLER0099";

    // seller Registration.
    @Override
    public ApiResponseWithMessage add(SellerRegister sellerRegister)
    {
        Seller existingSeller = sellerRepository.findByEmail(sellerRegister.getEmail());

        if (existingSeller != null) {
            throw new ResourceAlreadyExistsException("Seller already registered");
        }

        if (!sellerkey.equals(sellerRegister.getSellerkey())) {
            throw new ResourceIncorrectException("Seller Key Wrong!");
        }

        Seller seller = AppUtils.toSellerEntity(sellerRegister, passwordEncoder, roleRepository);
        seller.setSellerkey("SELLER0099");
        sellerRepository.save(seller);

        return new ApiResponseWithMessage(true,HttpStatus.CREATED,"Seller Registered Successfully.");
    }

    // seller Products.
    @Override
    public ApiResponseWithObject getProductsResponseBySeller()
    {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        Seller seller= sellerRepository.findByEmail(username);

        List<Product> products = productRepository.findBySellerSellerId(seller.getSellerId());
        if (products.isEmpty())
        {
            throw new ResourceNotFoundException("This seller hasn't added any products yet.");
        }
        return new ApiResponseWithObject(true,HttpStatus.OK,products);
    }


    // seller profile
    @Override
    public ApiResponseWithObject getSellerProfile()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Seller seller = sellerRepository.findByEmail(username);

        SellerProfileDto profile = AppUtils.toSellerProfileDto(seller);
        return new ApiResponseWithObject(true,HttpStatus.OK,profile);
    }


    //update Seller.
    @Transactional
    @Override
    public ApiResponseWithMessage updateSellerDetails(SellerUpdateDTO dto)
    {
        // Step 1: Extract email from JWT token
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Step 2: Fetch the seller by email
        Seller seller = sellerRepository.findByEmail(email);

        // Step 3: Update fields conditionally
        if (dto.getName() != null && !dto.getName().isBlank()) {
            seller.setName(dto.getName());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            seller.setEmail(dto.getEmail());
        }

        if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
            seller.setPhone(dto.getPhone());
        }

        if (dto.getCompany() != null && !dto.getCompany().isBlank()) {
            seller.setCompany(dto.getCompany());
        }
        sellerRepository.save(seller);

        return new ApiResponseWithMessage(true,HttpStatus.OK,"Seller Updated Successfully.");
    }
}
