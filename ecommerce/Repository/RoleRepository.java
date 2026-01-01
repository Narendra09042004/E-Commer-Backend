package com.dmart.ecommerce.Repository;

import com.dmart.ecommerce.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer>
{
    Role findByName(String name);
    Boolean existsByName(String name);

}
