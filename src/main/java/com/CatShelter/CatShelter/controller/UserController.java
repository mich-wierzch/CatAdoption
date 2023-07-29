package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(path="/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping(path="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public RedirectView add(UserModel userModel){
       UserModel newUser = userService.addUser(userModel);
       return new RedirectView("/login");
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

}
