package com.CatShelter.CatShelter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long postId;
    private String catName;
    private String catSex;
    private Integer catAge;
    private String catBreed;
    private String imageUrl;
    private String description;
    private String location;
    private LocalDate createdAt;
    private String userFirstName;
    private String userLastName;
    private String userMobilePhone;
}
