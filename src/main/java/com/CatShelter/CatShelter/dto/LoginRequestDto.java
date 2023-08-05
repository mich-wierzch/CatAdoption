package com.CatShelter.CatShelter.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
