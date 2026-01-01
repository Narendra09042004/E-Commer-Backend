package com.dmart.ecommerce.Service.ServiceImpls;

import com.dmart.ecommerce.Exception.ResourceAlreadyExistsException;
import com.dmart.ecommerce.Exception.ResourceIncorrectException;
import com.dmart.ecommerce.Exception.ResourceNotFoundException;
import com.dmart.ecommerce.DTO.AdminDTO.*;
import com.dmart.ecommerce.Utils.AppUtils;
import com.dmart.ecommerce.DTO.OrdersDTO.OrderForAdmin.OrderDTO;
import com.dmart.ecommerce.DTO.OrdersDTO.CancelOrderDTO.OrderAdminDTO;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithObject;
import com.dmart.ecommerce.Entity.*;
import com.dmart.ecommerce.Service.AdminService;
import com.dmart.ecommerce.Repository.*;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.config.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService
{
    private final AdminRepository adminRepository;

    private final SellerRepository sellerRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;


    // this the admin Key
    private final static String adminkey="ADMIN0099";

    // Register admin.
    @Override
    public ApiResponseWithMessage add(AdminRegistration adminRegistration)
    {
        Admin existingAdmin = adminRepository.findByEmail(adminRegistration.getEmail());

        if (existingAdmin != null)
        {
            throw new RuntimeException("User already exists!");
        }

        if (!adminkey.equals(adminRegistration.getAdminkey()))
        {
            throw new ResourceIncorrectException("Admin Key Incorrect");
        }

        Admin newAdmin = AppUtils.toEntity(adminRegistration, passwordEncoder, "ADMIN0099",roleRepository);
        adminRepository.save(newAdmin);

        return new ApiResponseWithMessage(true, HttpStatus.CREATED, "Admin Registered Successfully");
    }

    //update admin.
    @Transactional
    @Override
    public ApiResponseWithObject updateAdmin(AdminUpdateDto dto, CustomUserDetails currentUser)
    {
        String email= currentUser.getEmail();
        Admin admin = adminRepository.findByEmail(email);
        if(admin== null)
        {
            throw new ResourceNotFoundException("Admin Not Found");
        }

        if(dto.getName() != null && !dto.getName().isBlank())
        {
            admin.setName(dto.getName());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank())
        {
            admin.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null && !dto.getPhone().isBlank())
        {
            admin.setPhone(dto.getPhone());
        }
        return new ApiResponseWithObject(true,HttpStatus.OK,"Admin Updated Successfully");
    }

    // product List for admin
    @Override
    public ApiResponseWithObject getProductForAdmin()
    {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty())
        {
            throw new ResourceNotFoundException("No Product Added!");
        }
        products.stream().map(AppUtils::EntitytoProductForAdmin).collect(Collectors.toList());

        return new ApiResponseWithObject(true,HttpStatus.OK,products);
    }



    // List of Sellers
    @Override
    public ApiResponseWithObject getAllSellersView()
    {
        List<SellerForAdmin> seller = sellerRepository.findAllBy();
        if(seller.isEmpty())
        {
            throw new ResourceNotFoundException("No Seller Registered");
        }
        return new ApiResponseWithObject(true,HttpStatus.OK,seller);
    }

    // delete seller.
    @Override
    public ApiResponseWithObject deleteSellerByAdmin(Integer sellerId)
    {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller Not Found with id:" + sellerId));

        Integer productCount = productRepository.countBySeller_SellerId(sellerId);
        if (productCount > 0)
        {
            throw new ResourceAlreadyExistsException("Product Left In the Sellers Shop");
        }

        sellerRepository.deleteById(sellerId);
        return new ApiResponseWithObject(true,HttpStatus.OK,"Seller Deleted Successfully");
    }


    // all orders For admin
    @Transactional
    @Override
    public ApiResponseWithObject getAllOrderDTOs()
    {
        List<Orders> orders = orderRepository.findAllByOrderByOrderdateDesc();

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No Orders");
        }

        List<OrderDTO> data = orders.stream().map(AppUtils::OrderEntitytoOrderDTO).collect(Collectors.toList());

        return new ApiResponseWithObject(true,HttpStatus.OK,data);
    }


    // List of Cancel Orders.
    @Transactional
    @Override
    public ApiResponseWithObject getCancelledOrders()
    {
        List<Orders> orders = orderRepository.findByStatus("Cancelled");

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("There Is No Cancel Product!");
        }

        orders.forEach(order -> Hibernate.initialize(order.getItems()));

        orders.stream().map(AppUtils::OrdersEntitytoOrderAdminDTO).collect(Collectors.toList());

         return new ApiResponseWithObject(true,HttpStatus.OK,orders);
    }


    // List of Returned Order.
    @Transactional
    @Override
    public ApiResponseWithObject getReturnedOrders()
    {
        List<Orders> orders = orderRepository.findByStatus("returned");

        if(orders.isEmpty())
        {
            throw new ResourceNotFoundException("There is No Fully Returned Product");
        }

        orders.forEach(order -> Hibernate.initialize(order.getItems()));

        List<OrderAdminDTO> data = orders.stream().map(AppUtils::OrdersEntitytoOrderAdminDTO).collect(Collectors.toList());

        return new ApiResponseWithObject(true,HttpStatus.OK,orders);
    }

    // List of user.
    @Override
    public ApiResponseWithObject userForAdmin()
    {
        List<User> users = userRepository.findByUseridGreaterThan(0);

        if(users.isEmpty())
        {
            throw new ResourceNotFoundException("No user Registered!");
        }
        List<UserForAdmin> data = users.stream().map(AppUtils::toUserForAdmin).collect(Collectors.toList());
        return new ApiResponseWithObject(true,HttpStatus.OK,data);
    }

    // delete Admin
    @Override
    public ApiResponseWithObject deleteAdmin(Integer id)
    {
        Optional<Admin> admin=adminRepository.findById(id);

        if(admin.isEmpty())
        {
            throw new ResourceNotFoundException("Email of Admin Not found");
        }
        else
        {
            adminRepository.deleteById(id);
            return new ApiResponseWithObject(true,HttpStatus.OK,"Admin Deleted Successfully");
        }
    }
}
