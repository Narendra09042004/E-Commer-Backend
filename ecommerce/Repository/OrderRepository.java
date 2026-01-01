package com.dmart.ecommerce.Repository;

import com.dmart.ecommerce.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer>
{
    // find user
    List<Orders> findByUserUserid(Integer userId);
    // find status
    List<Orders> findByStatus(String status);

    List<Orders> findAllByOrderByOrderdateDesc();
}
