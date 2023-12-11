package com.exam.examserver.controllers;

import com.exam.examserver.entities.User;
import com.exam.examserver.exception.LoginPasswordException;
import com.exam.examserver.payloads.JwtAuthenticationRequest;
import com.exam.examserver.payloads.JwtAuthenticationResponse;
import com.exam.examserver.payloads.UserDto;
import com.exam.examserver.security.CustomUserDetailsService;
import com.exam.examserver.security.JwtTokenHelper;
import com.exam.examserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/authentication")
@CrossOrigin({"http://localhost:4200","http://localhost:9091"})
public class AuthenticationController {

    private final UserService userService;
    private final JwtTokenHelper jwtTokenHelper;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,JwtTokenHelper jwtTokenHelper,
                                    CustomUserDetailsService customUserDetailsService , UserService userService){
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenHelper = jwtTokenHelper;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> createToken(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest){
        this.authenticate(jwtAuthenticationRequest.getUsername(),jwtAuthenticationRequest.getPassword());
        String token = jwtTokenHelper.generateToken(jwtAuthenticationRequest.getUsername());
        return new ResponseEntity<>(new JwtAuthenticationResponse(token), HttpStatus.OK);
    }
    private void authenticate(String username , String password){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }
        catch (BadCredentialsException badCredentialsException){
            throw new LoginPasswordException(username);
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){
        return new ResponseEntity<>(this.userService.userToUserDto((User) customUserDetailsService.loadUserByUsername(principal.getName())),HttpStatus.OK);
    }

}
