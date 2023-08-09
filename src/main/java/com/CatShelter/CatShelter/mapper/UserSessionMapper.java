package com.CatShelter.CatShelter.mapper;

import com.CatShelter.CatShelter.dto.UserSessionDto;
import com.CatShelter.CatShelter.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserSessionMapper {

    public UserSessionDto convertUserSessionToDto(UserModel userModel){
        return UserSessionDto.builder()
                .userId(userModel.getUserId())
                .email(userModel.getEmail())
                .username(userModel.getUsername())
                .userRole(userModel.getUserRole())
                .build();
    }
}
