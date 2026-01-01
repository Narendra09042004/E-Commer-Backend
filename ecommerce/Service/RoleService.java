package com.dmart.ecommerce.Service;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithList;
import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.DTO.RoleDTO.CreateRoleDTO;
import com.dmart.ecommerce.Entity.Role;

public interface RoleService
{
    ApiResponseWithMessage createRole(CreateRoleDTO dto);
    ApiResponseWithMessage updateRole(Integer id, Role roleDetails);
    ApiResponseWithMessage deleteRole(Integer id);

    ApiResponseWithList getAllRoles();
}
