package com.exam.examserver.controllers;

import com.exam.examserver.config.ApplicationConstans;
import com.exam.examserver.payloads.ApiResponse;
import com.exam.examserver.payloads.RoleDto;
import com.exam.examserver.payloads.UserDto;
import com.exam.examserver.services.RoleService;
import com.exam.examserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin({"http://localhost:4200","http://localhost:9091"})
public class UserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private RoleDto roleDto;

    @Autowired
    public UserController(UserService userService,RoleService roleService,RoleDto roleDto,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.roleDto = roleDto;
    }

    // POST - Create User
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        List<RoleDto> roleDtos = new ArrayList<>();
        roleDto = roleService.getRoleById(ApplicationConstans.NORMAL_USER);
        roleDtos.add(roleDto);
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        //userDto.setEnabled(true);
        return new ResponseEntity<>(userService.createUser(userDto,roleDtos), HttpStatus.CREATED);
    }

    // PUT - Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto , @PathVariable("userId") int user_id){
        return new ResponseEntity<>(userService.updateUser(userDto,user_id),HttpStatus.OK);
    }

    // GET - Get All Users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    // GET - Get User By Username
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String user_name){
         return new ResponseEntity<>(userService.getUserByUsername(user_name),HttpStatus.OK);
    }

    // GET - Get User By ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") int user_id){
        return new ResponseEntity<>(userService.getUserById(user_id),HttpStatus.OK);
    }

    // DELETE - Delete User
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") int user_id){
        userService.deleteUserById(user_id);
        return new ResponseEntity<>(new ApiResponse(String.format("User With ID : %d Deleted Successfully",user_id),true),HttpStatus.OK);
    }

}
