package com.CatShelter.CatShelter.dto;

import lombok.Data;

@Data
public class UpdateUserDetailsDto {
    private String username;
    private String firstName;
    private String lastName;
    private String mobile;

}
