package com.CatShelter.CatShelter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String mobile;

}
