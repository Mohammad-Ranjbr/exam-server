package com.exam.examserver.services.implementation;

import com.exam.examserver.entities.Role;
import com.exam.examserver.exception.ResourceNotFoundException;
import com.exam.examserver.payloads.RoleDto;
import com.exam.examserver.repositories.RoleRepository;
import com.exam.examserver.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private Role role;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,ModelMapper modelMapper,Role role){
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.role = role;
    }


    @Override
    public RoleDto getRoleById(int roleId) {
        role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role","ID",String .valueOf(roleId)));
        return this.roleToRoleDto(role);
    }

    @Override
    public Role roleDtoToRole(RoleDto roleDto){
        return modelMapper.map(roleDto,Role.class);
    }

    @Override
    public RoleDto roleToRoleDto(Role role){
        return modelMapper.map(role,RoleDto.class);
    }

}
