package com.CatShelter.CatShelter.dto;

import com.CatShelter.CatShelter.model.Location;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PostDto {
    private String name;
    private String gender;
    private Integer age;
    private String breed;
    private List<String> imageFile;
    private String description;
    private Location location;
    private LocalDate createdAt;
    private String userFirstName;
    private String userLastName;
    private String userMobilePhone;
    private Long userId;
}
