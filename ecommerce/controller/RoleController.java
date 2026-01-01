package com.dmart.ecommerce.controller;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithList;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.DTO.RoleDTO.CreateRoleDTO;
import com.dmart.ecommerce.Entity.Role;
import com.dmart.ecommerce.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
public class RoleController
{
    private final RoleService roleService;

    @PreAuthorize("hasRole('DEFAULT_ADMIN')")
    @PostMapping("create-role")
    public ResponseEntity<ApiResponseWithMessage> createRole(@RequestBody CreateRoleDTO role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }

    // Only ADMIN can update roles
    @PreAuthorize("hasRole('DEFAULT_ADMIN')")
    @PutMapping("/update-role/{id}")
    public ResponseEntity<ApiResponseWithMessage> updateRole(@PathVariable Integer id, @RequestBody Role roleDetails) {
        return ResponseEntity.ok(roleService.updateRole(id, roleDetails));
    }

    // Only ADMIN can delete roles
    @PreAuthorize("hasRole('DEFAULT_ADMIN')")
    @DeleteMapping("/delete-role/{id}")
    public ResponseEntity<ApiResponseWithMessage> deleteRole(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.deleteRole(id));
    }

    // Anyone authenticated can view roles
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/List-of-roles")
    public ResponseEntity<ApiResponseWithList> getAllRoles()
    {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
