package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.LoginRequestDto;
import com.CatShelter.CatShelter.dto.RegisterRequestDto;
import com.CatShelter.CatShelter.dto.UserDto;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @PostMapping(path="/login",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    String login(@RequestBody LoginRequestDto loginRequest){
        userService.loginUser(loginRequest, authenticationManager);
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
    public UserDto updateUserDetails(UserDto user){
        return userService.updateUserInformation(user);

    }

    @PostMapping(path="/update/password")
    public String updateUserPassword(@RequestParam String password){
        return userService.updatePassword(password);
    }

    @DeleteMapping(path="/delete")
    public UserDto deleteUser(){
        return userService.deleteUser();
    }

}
