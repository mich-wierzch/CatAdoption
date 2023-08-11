package com.CatShelter.CatShelter.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary("cloudinary://api_key:api_secret@cloud_name");
    }
}
