package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.model.PostImagesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImagesRepository extends JpaRepository<PostImagesModel, Long> {
}
