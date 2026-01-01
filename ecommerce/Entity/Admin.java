package com.dmart.ecommerce.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Admin
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer admin_id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String confirm;

    private String phone;

    private String adminkey;

//    @Enumerated(EnumType.STRING)
//    private role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

    private Boolean isactive;
}
