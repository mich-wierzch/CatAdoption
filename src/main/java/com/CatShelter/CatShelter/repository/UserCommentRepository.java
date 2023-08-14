package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.model.UserCommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCommentRepository extends JpaRepository<UserCommentModel, Long> {
    List<UserCommentModel> findByUserUserId(Long userId);
}
