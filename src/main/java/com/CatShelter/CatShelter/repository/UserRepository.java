package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
    UserModel findByEmail(String email);


    UserModel findByUserId(Long userId);
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
