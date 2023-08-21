package com.CatShelter.CatShelter.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostImageDto {
    private String image;
    @JsonProperty("isFeatured")
    private boolean isFeatured;
}
