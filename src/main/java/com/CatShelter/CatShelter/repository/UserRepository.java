package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    UserModel findByEmail(String email);

    UserModel findByUserId(Long userId);
    boolean existsByEmail(String email);

}
