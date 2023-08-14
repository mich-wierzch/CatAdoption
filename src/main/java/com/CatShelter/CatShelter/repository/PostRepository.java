package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.model.PostModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {

    List<PostModel> findByUserUserId(Long userId);
    Page<PostModel> findByUserUserId(Long userId, PageRequest pageable);
    PostModel findByPostId(Long postId);
}
