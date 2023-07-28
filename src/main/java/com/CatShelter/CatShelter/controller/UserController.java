package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping(path="/register")
    public UserModel add(UserModel userModel){
        return userService.addUser(userModel);
    }

//    @PostMapping(path="/register")
//    public UserModel add(UserModel userModel){
//        UserModel newUser = userService.addUser(userModel);
//        if (newUser!=null){
//            return ResponseEntity.ok("/main");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed!");
//        }
//    }

}
