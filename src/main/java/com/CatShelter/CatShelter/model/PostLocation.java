package com.CatShelter.CatShelter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLocation {
    private String city;
    private float latitude;
    private float longitude;
}
