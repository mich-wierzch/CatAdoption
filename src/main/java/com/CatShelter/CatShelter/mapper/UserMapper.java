package com.CatShelter.CatShelter.mapper;

import com.CatShelter.CatShelter.dto.UserDto;
import com.CatShelter.CatShelter.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto convertUserToDto(UserModel userModel){
        return UserDto.builder()
                .id(userModel.getUserId())
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .mobile(userModel.getMobile())
                .build();
    }
}
