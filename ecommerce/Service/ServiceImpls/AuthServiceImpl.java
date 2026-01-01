package com.dmart.ecommerce.Service.ServiceImpls;

import com.dmart.ecommerce.Exception.IllegalArgumentException;
import com.dmart.ecommerce.Exception.ResourceIncorrectException;
import com.dmart.ecommerce.Exception.ResourceNotFoundException;
import com.dmart.ecommerce.Entity.Admin;
import com.dmart.ecommerce.Entity.Seller;
import com.dmart.ecommerce.Entity.User;
import com.dmart.ecommerce.Repository.AdminRepository;
import com.dmart.ecommerce.Repository.SellerRepository;
import com.dmart.ecommerce.Repository.UserRepository;
import com.dmart.ecommerce.Service.AuthService;
import com.dmart.ecommerce.config.CustomUserDetails;
import com.dmart.ecommerce.config.CustomUserDetailsService;
import com.dmart.ecommerce.config.Jwt.JwtService;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.DTO.RequestDTO.AuthRequest;
import com.dmart.ecommerce.DTO.ResponseDTO.AuthResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService
{
    private final AuthenticationManager authMgr;
    private final CustomUserDetailsService uds;
    private final JwtService jwtService;

    @Autowired
    private OtpServiceImpl otpService;
    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private SellerRepository sellerRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthResponse login(AuthRequest dto)
    {
        try
        {
            Authentication auth = authMgr.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );

            CustomUserDetails u = (CustomUserDetails) uds.loadUserByUsername(dto.getEmail());
            String token = jwtService.generateToken(u);

            AuthResponse response = new AuthResponse(
                    true, HttpStatus.OK, "Login successful",
                    u.getName(), u.getEmail(), u.getPhone(), u.getRole(), "Bearer " + token
            );

            return response;
        }
        catch (BadCredentialsException ex)
        {
            throw new ResourceIncorrectException("Invalid email or password");
        }
    }


    private final Map<String, String> otpStore = new HashMap<>();

    // forget password.
    public ApiResponseWithMessage handleForgotPassword(String email, HttpSession session)
    {
        if (adminRepo.existsByEmail(email) || sellerRepo.existsByEmail(email) || userRepo.existsByEmail(email))
        {
            String otp = String.valueOf(new Random().nextInt(900000) + 100000);

            System.out.println("Generated OTP for " + email + " is: " + otp);

            otpStore.put(email, otp);

            session.setAttribute("resetEmail", email);

            return new ApiResponseWithMessage(true,HttpStatus.OK,"OTP generated. Check your console.");
        }
        throw new ResourceNotFoundException("Email Not found!");
    }


    public boolean verifyOtp(String email, String inputOtp)
    {
        String storedOtp = otpStore.get(email);
        return storedOtp != null && storedOtp.equals(inputOtp);
    }

    public void clearOtp(String email) {
        otpStore.remove(email);
    }

    public ApiResponseWithMessage resetPassword(String otp, String newPassword, HttpSession session)
    {
        String email = (String) session.getAttribute("resetEmail");

        if (email == null)
        {
            throw new ResourceNotFoundException("Session expired or email not found.");
        }
        if (!verifyOtp(email, otp))
        {
            throw new ResourceIncorrectException("Invalid OTP");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        String confirmPass = newPassword;

        boolean updated = false;

        if (adminRepo.existsByEmail(email))
        {
            Admin admin = adminRepo.findByEmail(email);
            admin.setPassword(encodedPassword);
            admin.setConfirm(confirmPass);
            adminRepo.save(admin);
            updated = true;
        }
        else if (sellerRepo.existsByEmail(email))
        {
            Seller seller = sellerRepo.findByEmail(email);
            seller.setPassword(encodedPassword);
            seller.setConfirm(confirmPass);
            sellerRepo.save(seller);
            updated = true;
        }
        else if (userRepo.existsByEmail(email))
        {
            User user = userRepo.findByEmail(email);
            user.setPassword(encodedPassword);
            user.setConfirm(confirmPass);
            userRepo.save(user);
            updated = true;
        }

        if (updated)
        {
            clearOtp(email);
            session.removeAttribute("resetEmail");
            return new ApiResponseWithMessage(true,HttpStatus.OK,"Password Reset Successful.");
        }
        else
        {
            throw new IllegalArgumentException("Something went wrong while updating the password!");
        }
    }


}
