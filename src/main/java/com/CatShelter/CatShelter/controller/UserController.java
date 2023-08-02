package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping(path="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public String add(UserModel userModel){
       UserModel newUser = userService.addUser(userModel);
       return "Registered";
    }

    @GetMapping(path="/get-username")
    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            return authentication.getName();
        } else {
            return "Guest";
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
