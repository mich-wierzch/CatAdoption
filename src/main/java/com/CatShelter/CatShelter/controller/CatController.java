package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.service.CatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/cats")
@RestController
@AllArgsConstructor
public class CatController {

    private final CatService catService;
    @GetMapping("/getBreeds")
    public List<String> getCatBreeds(){
        String filePath = "src/main/resources/catBreeds.txt";
        return catService.readCatBreeds(filePath);
    }
}
