package com.dmart.ecommerce.Repository;

import com.dmart.ecommerce.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer>
{
    Admin findByEmail(String email);

//    Optional<Admin> findByemail(String email);

    boolean existsByEmail(String email);
}
