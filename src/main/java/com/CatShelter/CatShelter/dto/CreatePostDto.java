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
public class CreatePostDto {
    private String name;
    private String gender;
    private Integer age;
    private String breed;
    private String imageFile;
    private String description;
    private String location;
    private LocalDate createdAt;

}