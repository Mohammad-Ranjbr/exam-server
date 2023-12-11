package com.exam.examserver.services;

import com.exam.examserver.entities.Role;
import com.exam.examserver.payloads.RoleDto;

public interface RoleService {

    RoleDto getRoleById(int roleId);
    Role roleDtoToRole(RoleDto roleDto);
    RoleDto roleToRoleDto(Role role);

}
