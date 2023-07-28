package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.model.CatModel;
import com.CatShelter.CatShelter.service.CatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cats")
@AllArgsConstructor
public class CatController {

    private final CatService catService;

    @PostMapping(path="/add/{userId}")
    public @ResponseBody CatModel add(@RequestBody CatModel catModel, @PathVariable Long userId){
        return catService.addCat(catModel, userId);
    }

    @GetMapping(path="/getAllCats")
    public List<CatModel> displayCats(){
        return catService.getCatsList();
    }


}
