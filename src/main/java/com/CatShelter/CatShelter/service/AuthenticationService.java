package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.model.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public Authentication getCurrentAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getCurrentUserId(){
        Authentication authentication = getCurrentAuthentication();
        return ((UserModel) authentication.getPrincipal()).getUserId();
    }
}
