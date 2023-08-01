package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<PostModel, Long> {

    List<PostModel> findByUserUserId(Long userId);
    List<PostModel> findByPostId(Long postId);
}
