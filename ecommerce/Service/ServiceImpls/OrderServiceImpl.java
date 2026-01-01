package com.dmart.ecommerce.Service.ServiceImpls;

import com.dmart.ecommerce.Exception.IllegalArgumentException;
import com.dmart.ecommerce.Exception.ResourceIncorrectException;
import com.dmart.ecommerce.Exception.ResourceNotFoundException;
import com.dmart.ecommerce.DTO.OrdersDTO.*;
import com.dmart.ecommerce.DTO.OrdersDTO.OrderItemDTO;
import com.dmart.ecommerce.Entity.*;
import com.dmart.ecommerce.Service.OrderService;
import com.dmart.ecommerce.Repository.*;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final OrderitemRepository orderitemRepository;


    // place order.
    @Override
    public ApiResponseWithMessage placeorder(OrderRequestDTO request_product)
    {
        //if(loginRepository.findByemail(request_product.getEmail())!=null)
        //{
            double total_price=0;
            int total_stock=0;

            String email= SecurityContextHolder.getContext().getAuthentication().getName();
            User user=userRepository.findByEmail(email);


            List<OrderItem> orderitems_list = new ArrayList<>();
            List<OrderResponseDTO1> dto1 = new ArrayList<>();

            Orders order=new Orders();
            order.setUser(user);
            order.setItems(orderitems_list);
            order.setAmount(0.0);
            order.setAdress(user.getAddress());
            order.setStatus("CONFIRM");
            orderRepository.save(order);
            //------------------------------------------------------------------------------------

            for(OrderPlaceDTO orderPlaceDTO1 : request_product.getProducts())
            {
                Product product_to_place = new Product();

                if(productRepository.findById(orderPlaceDTO1.getProductid()).isPresent())
                {
                    product_to_place = productRepository.findById(orderPlaceDTO1.getProductid()).get();

                    if(orderPlaceDTO1.getQuantity() < product_to_place.getQuantity())
                    {
                        if(orderPlaceDTO1.getQuantity() > 0)
                        {
                            OrderResponseDTO1 dto2 = new OrderResponseDTO1();
                            dto2.setProductName(product_to_place.getName());
                            dto2.setQuantity(orderPlaceDTO1.getQuantity());
                            dto1.add(dto2);

                            OrderItem orderitem1 = new OrderItem();
                            orderitem1.setOrders(order);
                            orderitem1.setProduct(product_to_place);
                            product_to_place.setQuantity(product_to_place.getQuantity() - orderPlaceDTO1.getQuantity());
                            productRepository.save(product_to_place);


                            orderitem1.setQuantity(orderPlaceDTO1.getQuantity());
                            total_price = total_price + orderPlaceDTO1.getQuantity() * product_to_place.getPrice();
                            total_stock = total_stock + orderPlaceDTO1.getQuantity();
                            orderitem1.setPrice(product_to_place.getPrice());
                            orderitems_list.add(orderitem1);
                        }
                        else
                        {
                            orderRepository.deleteById(order.getOrderid());
                            throw new IllegalArgumentException("Order Quantity must be > 0");
                        }
                    }
                   else
                   {
                        orderRepository.deleteById(order.getOrderid());
                        //return "Product stock not avaiable! ";
                       throw new ResourceNotFoundException("Product Out of Stock");
                    }
                }
                else
                {
                    orderRepository.deleteById(order.getOrderid());
                    //return "Product not found! ";
                    throw new ResourceNotFoundException("Product not Avilable");
                }
            } // for loop ends.

            if(orderRepository.findById(order.getOrderid()).isPresent())
            {
                OrderItemDTO order1 = new OrderItemDTO();
                Orders orders = orderRepository.findById(order.getOrderid()).get();

                orders.setItems(orderitems_list);
                orders.setAmount(total_price);
                orderRepository.save(orders);
                return new ApiResponseWithMessage(true, HttpStatus.OK,"Ordered Successfully");
            }
            throw new ResourceIncorrectException("Order Not Placed Due To Some Inapropriate input");
        //}
        //throw new ResourceNotFoundException("User not login!");
    }



    // cancel order
//    @Override
//    public ApiResponseWithMessage cancelOrder(Integer orderId)
//    {
//        Optional<Orders> orderOpt = orderRepository.findById(orderId);
//
//        if (orderOpt.isEmpty())
//        {
//            throw new ResourceNotFoundException("Order not found.");
//        }
//
//        Orders order = orderOpt.get();
//
//        // Check status first
//        if (!order.getStatus().equalsIgnoreCase("confirm"))
//        {
//            throw new ResourceIncorrectException("Only confirm orders can be cancelled.");
//        }
//
//        // Get current time and order dates
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime orderDate = order.getOrderdate();
//        LocalDateTime placedDate = order.getPlacedDate();
//
//        // Allow cancellation only between orderDate and placedDate
//        if (now.isAfter(orderDate) && now.isBefore(placedDate))
//        {
//            order.setStatus("cancelled");
//            orderRepository.save(order);
//            //return "Order cancelled successfully.";
//            return new ApiResponseWithMessage(true,HttpStatus.OK,"Order cancelled Successfully");
//        }
//        else
//        {
//            throw new ResourceIncorrectException("Order cannot be cancelled now. It must be cancelled between the order date and the placed date.");
//        }
//    }

//    // cancel order.
//    @Override
//    public ApiResponseWithMessage cancelOrder(Integer orderId)
//    {
//        Optional<Orders> orderOpt = orderRepository.findById(orderId);
//
//        if (orderOpt.isEmpty()) {
//            throw new ResourceNotFoundException("Order not found.");
//        }
//
//        Orders order = orderOpt.get();
//
//        if (!order.getStatus().equalsIgnoreCase("confirm")) {
//            throw new ResourceIncorrectException("Only confirm orders can be cancelled.");
//        }
//
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime orderDate = order.getOrderdate();
//        LocalDateTime placedDate = order.getPlacedDate();
//
//        if (now.isAfter(orderDate) && now.isBefore(placedDate))
//        {
//            order.setStatus("cancelled");
//            orderRepository.save(order);
//
//            // Optional return of updated order data
//            OrderDTO updatedDto = ManualMapping.orderEntityToDTO(order);
//            return new ApiResponseWithMessage(true, HttpStatus.OK, "Order cancelled Successfully");
//        } else {
//            throw new ResourceIncorrectException("Order cannot be cancelled now. It must be cancelled between the order date and the placed date.");
//        }
//    }

    // cancel order.
    @Override
    public ApiResponseWithMessage cancelOrder(Integer orderId)
    {
        Optional<Orders> orderOpt = orderRepository.findById(orderId);

        if (orderOpt.isEmpty()) {
            throw new ResourceNotFoundException("Order not found.");
        }

        Orders order = orderOpt.get();

        if (!order.getStatus().equalsIgnoreCase("confirm")) {
            throw new ResourceIncorrectException("Only confirm orders can be cancelled.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime orderDate = order.getOrderdate();
        LocalDateTime placedDate = order.getPlacedDate();

        if (now.isAfter(orderDate) && now.isBefore(placedDate)) {

            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                int currentQuantity = product.getQuantity();
                int orderedQuantity = item.getQuantity();

                product.setQuantity(currentQuantity + orderedQuantity); // Add back quantity
                productRepository.save(product); // Save updated product
            }

            order.setStatus("cancelled");
            orderRepository.save(order);

            return new ApiResponseWithMessage(true, HttpStatus.OK, "Order cancelled Successfully");
        } else {
            throw new ResourceIncorrectException("Order cannot be cancelled now. It must be cancelled between the order date and the placed date.");
        }
    }


    // return single product.
    @Override
    public ApiResponseWithMessage returnProductFromOrder(Integer orderId, Integer productId)
    {
        Optional<Orders> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
        {
            throw new ResourceNotFoundException("Order Not Found");
        }

        Orders order = optionalOrder.get();
        String status = order.getStatus().toLowerCase();
        if (!status.equals("placed") && !status.equals("confirm"))
        {
            throw new ResourceNotFoundException("Not Placed And Confirm Order Found.");
        }

        Optional<OrderItem> optionalItem = order.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();

        if (optionalItem.isEmpty())
        {
            throw new ResourceNotFoundException("Product Not Found In This Order.");
        }

        OrderItem itemToReturn = optionalItem.get();

        Product product = itemToReturn.getProduct();
        product.setQuantity(product.getQuantity() + itemToReturn.getQuantity());

        order.getItems().remove(itemToReturn);
        orderitemRepository.delete(itemToReturn);

        if (order.getItems().isEmpty())
        {
            order.setStatus("returned");
        }

        productRepository.save(product);
        orderRepository.save(order);

        Integer sellerId = product.getSeller().getSellerId();

        return new ApiResponseWithMessage(true, HttpStatus.OK, "Product Returned Successfully");
    }


    // Delete Order.
    @Override
    public ApiResponseWithMessage deleteOrderIfEligible(Integer orderId)
    {
        Optional<Orders> orderOpt = orderRepository.findById(orderId);

        if (orderOpt.isEmpty())
        {
            throw new ResourceNotFoundException("Order Not Found");
        }

        Orders order = orderOpt.get();
        String status = order.getStatus().toLowerCase();

        if (!status.equals("cancelled") && !status.equals("returned"))
        {
            //return "Only cancelled & returned orders can be deleted.";
            throw new ResourceIncorrectException("Only cancelled & returned orders can be deleted.");
        }

        orderRepository.delete(order);
//        return "Order successfully deleted";
        return new ApiResponseWithMessage(true,HttpStatus.OK,"Product Deleted Successfully");
    }
}
