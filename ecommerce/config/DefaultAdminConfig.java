package com.dmart.ecommerce.config;

import com.dmart.ecommerce.Entity.Admin;
import com.dmart.ecommerce.Entity.Role;
import com.dmart.ecommerce.Repository.AdminRepository;
import com.dmart.ecommerce.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DefaultAdminConfig
{
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public CommandLineRunner createDefaultAdmin(AdminRepository adminRepository)
    {
        return args ->
        {
            String defaultEmail = "admin@email.com";
            if (adminRepository.findByEmail(defaultEmail) == null)
            {
                Admin admin = new Admin();
                Role adminRole = roleRepository.findByName("DEFAULT_ADMIN");
                if(adminRole == null)
                {
                    adminRole = new Role();
                    adminRole.setName("DEFAULT_ADMIN");
                    roleRepository.save(adminRole);
                }


                admin.setName("Default Admin");
                admin.setEmail(defaultEmail);
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setConfirm("admin123");
                admin.setPhone("9999999999");
                admin.setAdminkey("ADMIN0099");
                admin.setRole(adminRole);
                admin.setIsactive(true);

                System.out.println("Default admin Created!");

                adminRepository.save(admin);
            }
        };
    }
}
