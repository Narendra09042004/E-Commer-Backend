package com.dmart.ecommerce.Utils;

import com.dmart.ecommerce.Exception.ResourceNotFoundException;
import com.dmart.ecommerce.DTO.AdminDTO.AdminRegistration;
import com.dmart.ecommerce.DTO.AdminDTO.ProductForAdmin;
import com.dmart.ecommerce.DTO.AdminDTO.UserForAdmin;
import com.dmart.ecommerce.DTO.OrdersDTO.Bill.BillItemDTO;
import com.dmart.ecommerce.DTO.OrdersDTO.Bill.OrderBillDTO;
import com.dmart.ecommerce.DTO.OrdersDTO.CancelOrderDTO.OrderAdminDTO;
import com.dmart.ecommerce.DTO.OrdersDTO.OrderForAdmin.OrderDTO;
import com.dmart.ecommerce.DTO.OrdersDTO.OrderForAdmin.OrderItemDTO;
import com.dmart.ecommerce.DTO.ProductDTO.AddProductDTO;
import com.dmart.ecommerce.DTO.ProductDTO.ProductListForAll;
import com.dmart.ecommerce.DTO.sellerDTO.SellerProfileDto;
import com.dmart.ecommerce.DTO.sellerDTO.SellerRegister;
import com.dmart.ecommerce.DTO.userDTO.ClintProfile;
import com.dmart.ecommerce.DTO.userDTO.UserRegistration;
import com.dmart.ecommerce.DTO.userDTO.orderData.UserOrderItemDTO;
import com.dmart.ecommerce.DTO.userDTO.orderData.UserOrderSummaryDTO;
import com.dmart.ecommerce.Entity.*;
import com.dmart.ecommerce.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AppUtils
{
    //------------------------------------------------------------------------------------------------------------------
    //                               Admin Mapping

    public static Admin toEntity(AdminRegistration dto, PasswordEncoder passwordEncoder, String adminKey,RoleRepository roleRepository)
    {
        Admin admin = new Admin();
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setConfirm(dto.getPassword());
        admin.setPhone(dto.getPhone());
        admin.setIsactive(true);
        admin.setAdminkey(adminKey);

        Role adminRole = roleRepository.findByName("ADMIN");
        if(adminRole == null)
        {
            throw new ResourceNotFoundException("ROLE_ADMIN not found in database");
        }
        admin.setRole(adminRole);

        return admin;
    }

    public static ProductForAdmin EntitytoProductForAdmin(Product product)
    {
        ProductForAdmin dto = new ProductForAdmin();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());

        if (product.getSeller() != null) {
            dto.setSellerId(product.getSeller().getSellerId());
        }

        return dto;
    }

    public static OrderDTO OrderEntitytoOrderDTO(Orders order) {
        OrderDTO dto = new OrderDTO();

        dto.setOrderId(order.getOrderid());
        dto.setUserId(order.getUser().getUserid());
        dto.setOrderDate(order.getOrderdate());
        dto.setPlacedDate(order.getPlacedDate());
        dto.setAmount(order.getAmount());
        dto.setStatus(order.getStatus());
        dto.setAddress(order.getUser().getAddress());

        List<OrderItemDTO> items = new ArrayList<>();
        for (OrderItem item : order.getItems())
        {
            OrderItemDTO itemDTO = new OrderItemDTO();

            itemDTO.setProductId(item.getProduct().getProductId().intValue());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            items.add(itemDTO);
        }
        dto.setItems(items);
        return dto;
    }


    public static OrderAdminDTO OrdersEntitytoOrderAdminDTO(Orders order)
    {
        OrderAdminDTO dto = new OrderAdminDTO();

        dto.setOrderId(order.getOrderid());
        dto.setUserId(order.getUser().getUserid());
        dto.setAmount(order.getAmount());
        dto.setStatus(order.getStatus());
        dto.setOrderDate(order.getOrderdate());

        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(AppUtils::toOrderItemDTO)
                .collect(Collectors.toList());

        dto.setItems(itemDTOs);

        return dto;
    }

    public static OrderItemDTO toOrderItemDTO(OrderItem item)
    {
        return new OrderItemDTO(
                item.getProduct().getProductId(),
                item.getQuantity(),
                item.getPrice()
        );
    }

    public static UserForAdmin toUserForAdmin(User user)
    {
        UserForAdmin dto = new UserForAdmin();
        dto.setId(user.getUserid());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setNumber(user.getPhone());
        dto.setAddress(user.getAddress());
        return dto;
    }

    //------------------------------------------------------------------------------------------------------------------
    //                                  Sellers Mapping

    public static Seller toSellerEntity(SellerRegister sellerRegister, PasswordEncoder passwordEncoder,RoleRepository roleRepository)
    {
        Seller seller = new Seller();
        seller.setName(sellerRegister.getName());
        seller.setEmail(sellerRegister.getEmail());
        seller.setPassword(passwordEncoder.encode(sellerRegister.getPassword()));
        seller.setConfirm(sellerRegister.getPassword());
        seller.setPhone(sellerRegister.getPhone());
        seller.setCompany(sellerRegister.getCompany());

        Role sellerRole = roleRepository.findByName("SELLER");
        if(sellerRole == null)
        {
            throw new ResourceNotFoundException("SELLER role not found!");
        }

        return seller;
    }


    public static SellerProfileDto toSellerProfileDto(Seller seller)
    {
        SellerProfileDto dto = new SellerProfileDto();
        dto.setSellerid(seller.getSellerId());
        dto.setName(seller.getName());
        dto.setEmail(seller.getEmail());
        dto.setPhone(seller.getPhone());
        dto.setCompany(seller.getCompany());
        dto.setCreatedDate(seller.getDate());
        return dto;
    }

    //------------------------------------------------------------------------------------------------------------------
    //                                  Users Mapping

    public static User toUser(UserRegistration dto, PasswordEncoder encoder,RoleRepository roleRepository)
    {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setConfirm(dto.getPassword());
        user.setPhone(dto.getNumber());
        user.setAddress(dto.getAddress());
        Role userRole = roleRepository.findByName("USER");

        if(userRole == null)
        {
            throw new ResourceNotFoundException("USER role not found");
        }

        return user;
    }


    public static ClintProfile toClintProfile(User user)
    {
        ClintProfile profile = new ClintProfile();
        profile.setName(user.getName());
        profile.setEmail(user.getEmail());
        profile.setNumber(user.getPhone());
        profile.setAddress(user.getAddress());
        return profile;
    }

    public static OrderBillDTO toOrderBillDTO(Orders order, Integer orderId)
    {
        OrderBillDTO bill = new OrderBillDTO();
        bill.setOrderId(order.getOrderid());
        bill.setOrderDate(order.getOrderdate());
        bill.setTotalAmount(order.getAmount());

        User user = order.getUser();
        bill.setUserId(user.getUserid());
        bill.setUserName(user.getName());
        bill.setUserAddress(user.getAddress());

        List<BillItemDTO> itemList = order.getItems().stream().map(item -> {
            BillItemDTO billItem = new BillItemDTO();
            billItem.setProductName(item.getProduct().getName());
            billItem.setPricePerItem(item.getPrice());
            billItem.setQuantity(item.getQuantity());
            billItem.setTotalPrice(item.getPrice() * item.getQuantity());
            return billItem;
        }).collect(Collectors.toList());

        bill.setItems(itemList);

        return bill;
    }


    public static List<UserOrderSummaryDTO> toUserOrderSummaryListWithoutResponseFields(List<Orders> ordersList) {
        List<UserOrderSummaryDTO> result = new ArrayList<>();

        for (Orders order : ordersList) {
            UserOrderSummaryDTO dto = new UserOrderSummaryDTO();
            dto.setOrderId(order.getOrderid());
            dto.setOrderDate(order.getOrderdate());
            dto.setTotalAmount(order.getAmount());
            dto.setOrderStatus(order.getStatus());

            List<UserOrderItemDTO> itemList = new ArrayList<>();
            for (OrderItem item : order.getItems()) {
                UserOrderItemDTO itemDTO = new UserOrderItemDTO();
                itemDTO.setProductName(item.getProduct().getName());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setPricePerItem(item.getPrice());
                itemDTO.setTotalItemPrice(item.getPrice() * item.getQuantity());
                itemList.add(itemDTO);
            }

            dto.setItems(itemList);
            result.add(dto);
        }

        return result;
    }

    //------------------------------------------------------------------------------------------------------------------
    //                                     Product Mapping

    public static Product toProduct(AddProductDTO dto, Seller seller)
    {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setSeller(seller);
        return product;
    }

    public static List<ProductListForAll> mapProductsForAllUser(List<Product> products)
    {
        List<ProductListForAll> result = new ArrayList<>();

        for (Product product : products) {
            ProductListForAll dto = new ProductListForAll();
            dto.setId(product.getProductId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setQuantity(product.getQuantity());

            result.add(dto);
        }

        return result;
    }

    //------------------------------------------------------------------------------------------------------------------
    //                                  Order Mapping

    public static OrderDTO orderEntityToDTO(Orders order)
    {
        OrderDTO dto = new OrderDTO();

        dto.setOrderId(order.getOrderid());
        dto.setUserId(order.getUser().getUserid());
        dto.setOrderDate(order.getOrderdate());
        dto.setPlacedDate(order.getPlacedDate());
        dto.setAmount(order.getAmount());
        dto.setStatus(order.getStatus());
        dto.setAddress(order.getUser().getAddress());

        return dto;
    }
}
