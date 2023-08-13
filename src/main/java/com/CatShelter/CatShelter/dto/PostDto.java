package com.CatShelter.CatShelter.dto;

import com.CatShelter.CatShelter.model.Location;
import com.CatShelter.CatShelter.model.PostImages;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PostDto {
    private String name;
    private String gender;
    private Integer age;
    private String breed;
    private PostImages imageFile;
    private String description;
    private Location location;
    private LocalDate createdAt;
    private String userFirstName;
    private String userLastName;
    private String userMobilePhone;
    private Long userId;


}
