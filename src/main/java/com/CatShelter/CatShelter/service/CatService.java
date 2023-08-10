package com.CatShelter.CatShelter.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CatService {

    public List<String> readCatBreeds(String filePath){
        List<String> catBreeds = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null) {
                catBreeds.add(line.trim());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return catBreeds;
    }
}
