package com.CatShelter.CatShelter.dto;

import com.CatShelter.CatShelter.model.PostLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class CreatePostDto {
    private String name;
    private String gender;
    private Integer age;
    private String breed;
    private List<String> images;
    private String description;
    private PostLocation location;
    private LocalDate createdAt;

}