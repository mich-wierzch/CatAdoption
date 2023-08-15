package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.dto.UserCommentDto;
import com.CatShelter.CatShelter.model.UserCommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserCommentRepository extends JpaRepository<UserCommentModel, Long> {
    List<UserCommentModel> findByUserUserId(Long userId);

    UserCommentModel findByCommentId(Long commentId);

}
