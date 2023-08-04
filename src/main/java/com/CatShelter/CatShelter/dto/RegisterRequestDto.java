package com.CatShelter.CatShelter.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class RegisterRequestDto {
    private String username;
    private String email;
    private String password;

}
