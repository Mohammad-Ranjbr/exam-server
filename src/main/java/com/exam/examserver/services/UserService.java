package com.exam.examserver.services;

import com.exam.examserver.entities.User;
import com.exam.examserver.payloads.RoleDto;
import com.exam.examserver.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto, List<RoleDto> role);
    UserDto updateUser(UserDto userDto,int userId);
    List<UserDto> getAllUsers();
    UserDto getUserById(int userId);
    UserDto getUserByUsername(String username);
    void deleteUserById(int userId);
    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);

}
