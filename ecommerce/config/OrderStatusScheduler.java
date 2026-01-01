package com.dmart.ecommerce.config;

import com.dmart.ecommerce.Entity.Orders;
import com.dmart.ecommerce.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderStatusScheduler {

    @Autowired
    private OrderRepository orderRepo;

    @Scheduled(cron = "0 * * * * *") // runs every minute
    public void updateExpiredPlacedOrders()
    {
        List<Orders> placedOrders = orderRepo.findByStatus("confirm");
        LocalDateTime now = LocalDateTime.now();

        for (Orders order : placedOrders)
        {
            if (now.isAfter(order.getPlacedDate()))
            {
                order.setStatus("placed");
                orderRepo.save(order);
            }
        }
    }
}
