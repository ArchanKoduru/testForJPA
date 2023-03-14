package com.gourmetoven.gourmetapp.repository;

import com.gourmetoven.gourmetapp.entity.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredients, Integer>, JpaSpecificationExecutor {
//    List<Ingredients> findByType(String type);
    Ingredients findByName(String names);
}
