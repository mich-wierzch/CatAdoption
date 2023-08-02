package com.CatShelter.CatShelter.request;

import lombok.Data;
import lombok.Getter;

@Getter
public class LoginRequest {
    String email;
    String password;
}
