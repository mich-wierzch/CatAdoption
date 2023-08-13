package com.CatShelter.CatShelter.dto;

import com.CatShelter.CatShelter.model.Location;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data

public class CreatePostDto {
    private String name;
    private String gender;
    private Integer age;
    private String breed;
    private List<String> imageFile;
    private String description;
    private Location location;
    private LocalDate createdAt;

}