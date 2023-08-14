package com.CatShelter.CatShelter.dto;

import com.CatShelter.CatShelter.model.PostLocation;
import com.CatShelter.CatShelter.model.PostImagesModel;
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
    private List<PostImageDto> images;
    private String description;
    private PostLocation location;
    private LocalDate createdAt;
    private String userFirstName;
    private String userLastName;
    private String userMobilePhone;
    private Long userId;


}
