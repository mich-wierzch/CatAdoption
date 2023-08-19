package com.CatShelter.CatShelter.dto;

import com.CatShelter.CatShelter.model.PostLocation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PostDto {

    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String breed;
    private List<String> images;
    private String description;
    private PostLocation location;
    private LocalDate createdAt;
    private String userFirstName;
    private String userLastName;
    private String userMobilePhone;
    private Long userId;


}
