package com.dmart.ecommerce.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Seller
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellerId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String confirm;

    private String phone;

    private String company;

    private String sellerkey;

//    @Enumerated(EnumType.STRING)
//    private role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

    private LocalDateTime date=LocalDateTime.now();
}
