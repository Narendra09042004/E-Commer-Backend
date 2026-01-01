package com.dmart.ecommerce.Repository;

import com.dmart.ecommerce.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderitemRepository extends JpaRepository<OrderItem,Integer>
{
}
