package com.dmart.ecommerce.Service.ServiceImpls;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithList;
import com.dmart.ecommerce.Exception.ResourceAlreadyExistsException;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.DTO.RoleDTO.CreateRoleDTO;
import com.dmart.ecommerce.Entity.Role;
import com.dmart.ecommerce.Repository.RoleRepository;
import com.dmart.ecommerce.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService
{
    private final RoleRepository roleRepository;

    public ApiResponseWithMessage createRole(CreateRoleDTO dto)
    {
        Role role = roleRepository.findByName(dto.getName());
        if (role != null)
        {
            throw new ResourceAlreadyExistsException("Role already exists: " + role.getName());
        }

        Role newRole = new Role();
        newRole.setName(dto.getName());

        roleRepository.save(newRole);

        return new ApiResponseWithMessage(true, HttpStatus.CREATED,"Role Created Successfully");
    }

    public ApiResponseWithMessage updateRole(Integer id, Role roleDetails)
    {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found: " + id));

        role.setName(roleDetails.getName());
         roleRepository.save(role);

         return new ApiResponseWithMessage(true,HttpStatus.OK,"Role Updated Successfully");
    }

    public ApiResponseWithMessage deleteRole(Integer id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found: " + id);
        }
        roleRepository.deleteById(id);
        return new ApiResponseWithMessage(true,HttpStatus.OK,"Role Deleted Successfully");
    }

    public ApiResponseWithList getAllRoles() {

        List<Role> roles = roleRepository.findAll();

        return new ApiResponseWithList(true, HttpStatus.OK,roles);
    }

}
