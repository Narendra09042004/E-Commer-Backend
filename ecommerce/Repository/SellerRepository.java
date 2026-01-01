package com.dmart.ecommerce.Repository;

import com.dmart.ecommerce.DTO.AdminDTO.SellerForAdmin;
import com.dmart.ecommerce.Entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer>
{
    Seller findByEmail(String email);

//    Optional<Seller> findByemail(String email);

    boolean existsByEmail(String email);

    List<SellerForAdmin> findAllBy();
}
