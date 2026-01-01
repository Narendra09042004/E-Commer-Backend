package com.dmart.ecommerce.Repository;

import com.dmart.ecommerce.DTO.AdminDTO.UserForAdmin;
import com.dmart.ecommerce.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{
    User findByEmail(String email);

//    Optional<User> findByemail(String email);

    List<User> findByUseridGreaterThan(int id);

    boolean existsByEmail(String email);
}
