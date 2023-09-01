package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.model.UserCommentModel;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserCommentRepository extends JpaRepository<UserCommentModel, Long> {

    Page<UserCommentModel> findByUserUserIdOrderByTimestampDesc(Long userId, PageRequest pageable);
    List<UserCommentModel> findByUserUserIdOrderByTimestampDesc(Long userId);

    UserCommentModel findByCommentId(Long commentId);

}
