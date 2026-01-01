package com.dmart.ecommerce.Service.ServiceImpls;

import com.dmart.ecommerce.Exception.ResourceAlreadyExistsException;
import com.dmart.ecommerce.Exception.ResourceNotFoundException;
import com.dmart.ecommerce.Utils.AppUtils;
import com.dmart.ecommerce.DTO.OrdersDTO.Bill.OrderBillDTO;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithList;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithObject;
import com.dmart.ecommerce.DTO.userDTO.*;
import com.dmart.ecommerce.DTO.userDTO.orderData.UserOrderSummaryDTO;
import com.dmart.ecommerce.Repository.RoleRepository;
import com.dmart.ecommerce.config.CustomUserDetails;
import com.dmart.ecommerce.Entity.Orders;
import com.dmart.ecommerce.Entity.User;
import com.dmart.ecommerce.Service.UserService;
import com.dmart.ecommerce.Repository.OrderRepository;
import com.dmart.ecommerce.Repository.UserRepository;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    // Register or Add user.
    @Override
    public ApiResponseWithMessage add_user(UserRegistration userRegistration)
    {
        User user_email=userRepository.findByEmail(userRegistration.getEmail());
        if(user_email!=null)
        {
            throw new ResourceAlreadyExistsException("user already registered");
        }
        else
        {
            User user = AppUtils.toUser(userRegistration, passwordEncoder, roleRepository);
            userRepository.save(user);

            return new ApiResponseWithMessage(true,HttpStatus.CREATED, "User Registered Successfully.");
        }
    }

    // Users profile
    @Override
    public ApiResponseWithObject get_clint_data(CustomUserDetails customUserDetails)
    {
        String email = customUserDetails.getEmail();
        User user = userRepository.findByEmail(email);

        ClintProfile profile = AppUtils.toClintProfile(user);
        return new ApiResponseWithObject(true,HttpStatus.OK,profile);
    }

    // update user.
    @Override
    public ApiResponseWithMessage update_data(Update_user updateUser)
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User userToUpdate = userRepository.findByEmail(email);

        if (updateUser.getEmail() != null && !updateUser.getEmail().isBlank()) {
            userToUpdate.setEmail(updateUser.getEmail());
        }

        if (updateUser.getNumber() != null && !updateUser.getNumber().isBlank()) {
            userToUpdate.setPhone(updateUser.getNumber());
        }

        if (updateUser.getAddress() != null && !updateUser.getAddress().isBlank()) {
            userToUpdate.setAddress(updateUser.getAddress());
        }

        if (updateUser.getPassword() != null && !updateUser.getPassword().isBlank()) {
            userToUpdate.setPassword(updateUser.getPassword());
        }

        userRepository.save(userToUpdate);

        return new ApiResponseWithMessage(true, HttpStatus.OK, "User updated successfully.");
    }

    // delete user
    @Override
    public ApiResponseWithMessage deleteUser()
    {
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(email);

        if(!orderRepository.findByUserUserid(user.getUserid()).isEmpty())
        {
            throw new ResourceAlreadyExistsException("User cannot be deleted because they have placed orders.");
        }

        userRepository.deleteById(user.getUserid());
        return new ApiResponseWithMessage(true,HttpStatus.OK,"User Deleted Successfully");
    }

    // Bill to the clint.
    @Override
    public ApiResponseWithObject getOrderBill(Integer orderId)
    {
        Optional<Orders> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty())
        {
            throw new ResourceNotFoundException("Order not found!");
        }

        Orders order = orderOpt.get();

        OrderBillDTO bill = AppUtils.toOrderBillDTO(order, orderId);

        if (bill.getItems().isEmpty()) {
            throw new ResourceNotFoundException("user return all product");
        }

        return new ApiResponseWithObject(true,HttpStatus.OK,bill);
    }

    // all order bill.
    @Override
    public ApiResponseWithList getOrdersByUser(CustomUserDetails curruntUser)
    {
        String email = curruntUser.getEmail();
        User user = userRepository.findByEmail(email);

        List<Orders> ordersList = orderRepository.findByUserUserid(user.getUserid());
        if (ordersList.isEmpty()) {
            throw new ResourceNotFoundException("You did not order Any thing yet!");
        }

        List<UserOrderSummaryDTO> result = AppUtils.toUserOrderSummaryListWithoutResponseFields(ordersList);

        return new ApiResponseWithList(true, HttpStatus.OK, result);
    }
}
