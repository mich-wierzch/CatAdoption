package com.CatShelter.CatShelter.dto;

import com.CatShelter.CatShelter.model.Location;
import com.CatShelter.CatShelter.model.PostImages;
import lombok.Data;

import java.time.LocalDate;

@Data

public class CreatePostDto {
    private String name;
    private String gender;
    private Integer age;
    private String breed;
    private PostImages imageFile;
    private String description;
    private Location location;
    private LocalDate createdAt;

}