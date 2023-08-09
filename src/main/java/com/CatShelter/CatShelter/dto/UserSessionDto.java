package com.CatShelter.CatShelter.dto;

import com.CatShelter.CatShelter.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserSessionDto {
    private Long userId;
    private String email;
    private String username;
    private UserRole userRole;

}
