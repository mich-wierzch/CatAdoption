package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.model.CatModel;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.service.CatService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cats")
@AllArgsConstructor
public class CatController {

    private final CatService catService;

    @PostMapping(path="/add")
    public RedirectView add(CatModel catModel){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((UserModel) authentication.getPrincipal()).getUserId();

        catService.addCat(catModel, userId);

        return new RedirectView("/main");
    }



}
