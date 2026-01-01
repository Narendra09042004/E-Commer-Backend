package com.dmart.ecommerce.config;

import com.dmart.ecommerce.Entity.Admin;
import com.dmart.ecommerce.Entity.Seller;
import com.dmart.ecommerce.Entity.User;
import com.dmart.ecommerce.Repository.AdminRepository;
import com.dmart.ecommerce.Repository.SellerRepository;
import com.dmart.ecommerce.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService
{
    private final AdminRepository adminRepo;
    private final SellerRepository sellerRepo;
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
     {
        // Try to find Admin
        Admin admin = adminRepo.findByEmail(email);
        if (admin != null) {
            return new CustomUserDetails(
                    admin.getName(),
                    admin.getEmail(),
                    admin.getPassword(),
                    admin.getPhone(),
                    admin.getRole().getName()
                    );
        }

        // Try to find Seller
        Seller seller = sellerRepo.findByEmail(email);
        if (seller != null) {
            return new CustomUserDetails(
                    seller.getName(),
                    seller.getEmail(),
                    seller.getPassword(),
                    seller.getPhone(),
                    seller.getRole().getName()
                    );
        }

        // Try to find User
        User user = userRepo.findByEmail(email);
        if (user != null) {
            return new CustomUserDetails(
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getPhone(),
                    user.getRole().getName()
                    );
        }

        // If not found in any repository
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

}