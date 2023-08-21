package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.LoginRequestDto;
import com.CatShelter.CatShelter.dto.RegisterRequestDto;
import com.CatShelter.CatShelter.dto.UserDto;
import com.CatShelter.CatShelter.dto.UserSessionDto;
import com.CatShelter.CatShelter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @CrossOrigin
    @PostMapping(path="/login",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest, HttpServletRequest request){
        return userService.loginUser(loginRequest, authenticationManager, request);
    }


    @PostMapping(path="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody RegisterRequestDto registerRequest){
      return userService.addUser(registerRequest);

    }

    @GetMapping(path="/session")
    public UserSessionDto userSession(){
        return userService.isUserSessionActive();
    }

    @GetMapping(path="/details/{userId}")
    public UserDto fetchUserDetails(@PathVariable Long userId){
        return userService.fetchUserInformation(userId);
    }


    @PatchMapping(path="/update-details")
    public UserDto updateUserDetails(UserDto user){
        return userService.updateUserInformation(user);

    }

    @PostMapping(path="/update-password")
    public String updateUserPassword(@RequestParam String oldPassword, @RequestParam String newPassword){
        return userService.updatePassword(oldPassword, newPassword);
    }

    @DeleteMapping(path="/delete")
    public UserDto deleteUser(@RequestParam String password,HttpServletRequest request){
        return userService.deleteUser(password,request);
    }



}
