package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.LoginRequestDto;
import com.CatShelter.CatShelter.dto.RegisterRequestDto;
import com.CatShelter.CatShelter.dto.UserDto;
import com.CatShelter.CatShelter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path="/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @PostMapping(path="/login",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    String login(@RequestBody LoginRequestDto loginRequest, HttpServletRequest request){
        return userService.loginUser(loginRequest, authenticationManager, request);
    }


    @PostMapping(path="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public String add(@RequestBody RegisterRequestDto registerRequest){
       userService.addUser(registerRequest);
       return "Registered";
    }

    @GetMapping(path="/session")
    public boolean isLoggedIn(){
        return userService.isUserSessionActive();
    }

    @GetMapping(path="/details/{userId}")
    public UserDto fetchUserDetails(@PathVariable Long userId){
        return userService.fetchUserInformation(userId);
    }


    @PatchMapping(path="/update/details")
    public UserDto updateUserDetails(UserDto user, Principal principal){
        return userService.updateUserInformation(user, principal);

    }

    @PostMapping(path="/update/password")
    public String updateUserPassword(@RequestParam String password, Principal principal){
        return userService.updatePassword(password, principal);
    }

    @DeleteMapping(path="/delete")
    public UserDto deleteUser(@RequestParam String password, Principal principal, HttpServletRequest request){
        return userService.deleteUser(password, principal, request);
    }



}
