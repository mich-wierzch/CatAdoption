package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.model.CatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<CatModel, Long> {
}
