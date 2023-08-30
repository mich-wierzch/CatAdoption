package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.*;
import com.CatShelter.CatShelter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

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


    @PostMapping(path="/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequest){
      return userService.registerUser(registerRequest);

    }

    @GetMapping(path="/session")
    public UserSessionDto userSession(){
        return userService.isUserSessionActive();
    }

    @GetMapping(path="/all-registered")
    public List<UserDto> findAllRegisteredUsers(){
        return userService.findAllUsers();
    }

    @GetMapping(path="/details/{userId}")
    public UserDto fetchUserDetails(@PathVariable Long userId){
        return userService.fetchUserInformation(userId);
    }


    @PatchMapping(path="/update-details")
    public ResponseEntity<String> updateUserDetails(@RequestBody UpdateUserDetailsDto userDetailsDto){
        return userService.updateUserInformation(userDetailsDto);

    }

    @PostMapping(path="/update-password")
    public ResponseEntity<String> updateUserPassword(@RequestParam String oldPassword, @RequestParam String newPassword){
        return userService.updatePassword(oldPassword, newPassword);
    }

    @DeleteMapping(path="/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String password,HttpServletRequest request){
        return userService.deleteUser(password,request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatchException(){
        return null;
    }




}
