package com.CatShelter.CatShelter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location{
    private String city;
    private float latitude;
    private float longitude;
}
