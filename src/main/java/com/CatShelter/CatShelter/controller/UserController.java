package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.LoginRequestDto;
import com.CatShelter.CatShelter.dto.RegisterRequestDto;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return "Logged in";
    }


    @PostMapping(path="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public String add(@RequestBody RegisterRequestDto registerRequest){
       userService.addUser(registerRequest);
       return "Registered";
    }

    @GetMapping(path="/session")
    public boolean isLoggedIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    @GetMapping(path="/get-username")
    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            return authentication.getName();
        } else {
            return null;
        }
    }

    @PatchMapping(path="/update")
    public UserModel updateUser(UserModel userModel){
        String email = SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();
        return userService.updateUser(userModel, email);

    }

    @DeleteMapping(path="/delete")
    public UserModel deleteUser(){
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userService.deleteUser(email);
    }

}
