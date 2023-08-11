package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.model.PostModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostModel, Long> {

    List<PostModel> findByUserUserId(Long userId);
    Page<PostModel> findByUserUserId(Long userId, PageRequest pageable);
    PostModel findByPostId(Long postId);
}
