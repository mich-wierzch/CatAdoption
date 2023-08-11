package com.CatShelter.CatShelter.dto;

import com.CatShelter.CatShelter.model.UserRole;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserSessionDto {
    private Long userId;
    private String email;
    private String username;
    private UserRole userRole;

}
