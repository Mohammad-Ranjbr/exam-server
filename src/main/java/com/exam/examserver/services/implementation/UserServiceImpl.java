package com.exam.examserver.services.implementation;

import com.exam.examserver.entities.User;
import com.exam.examserver.exception.ResourceFoundException;
import com.exam.examserver.exception.ResourceNotFoundException;
import com.exam.examserver.payloads.RoleDto;
import com.exam.examserver.payloads.UserDto;
import com.exam.examserver.repositories.UserRepository;
import com.exam.examserver.services.RoleService;
import com.exam.examserver.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private User savedUser;
    private User updatedUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,RoleService roleService,ModelMapper modelMapper,User savedUser,User updatedUser){
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.savedUser = savedUser;
        this.updatedUser = updatedUser;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto, List<RoleDto> roles){
        if(userRepository.findByUsername(userDto.getUsername()).isPresent()){
            System.out.println("User Already Exists.");
            throw new ResourceFoundException("User","Username",userDto.getUsername());
        }
        User user = this.userDtoToUser(userDto);
        for(RoleDto role:roles){
            RoleDto role1 = roleService.getRoleById(role.getRoleId());
            if(role1 == null) {
                roles.remove(role);
            }
        }
        user.setProfile("default.png");
        user.getRoles().addAll(roles.stream().map(roleService::roleDtoToRole).collect(Collectors.toList()));
        savedUser = userRepository.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","ID",String.valueOf(userId)));
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        updatedUser = userRepository.save(user);
        return this.userToUserDto(updatedUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::userToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","ID",String.valueOf(userId)));
        return this.userToUserDto(user);
    }


    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User","Username",username));
        return this.userToUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUserById(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","ID",String.valueOf(userId)));
        userRepository.delete(user);
    }

    @Override
    public User userDtoToUser(UserDto userDto){
        return modelMapper.map(userDto,User.class);
    }

    @Override
    public UserDto userToUserDto(User user){
        return modelMapper.map(user,UserDto.class);
    }

}
