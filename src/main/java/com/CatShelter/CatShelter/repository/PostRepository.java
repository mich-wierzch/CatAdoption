package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostModel, Long> {
}
