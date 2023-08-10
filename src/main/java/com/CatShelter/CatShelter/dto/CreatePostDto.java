package com.CatShelter.CatShelter.dto;

import lombok.Data;

import java.time.LocalDate;

@Data

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