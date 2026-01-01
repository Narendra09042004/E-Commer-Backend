package com.dmart.ecommerce.Repository;

import com.dmart.ecommerce.Entity.Product;
import com.dmart.ecommerce.Entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>
{
//    Optional<Product> findById(Integer productId);

    List<Product> findBySellerSellerId(Integer Sellerid);

    Product findByNameAndSeller_SellerId(String name, Integer sellerId);

    Integer countBySeller_SellerId(Integer sellerId);

    Product findByProductIdAndSeller_SellerId(Integer productId, Integer sellerId);
}
