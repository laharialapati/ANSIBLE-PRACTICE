package com.klef.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.dev.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    // JpaRepository provides CRUD methods out-of-the-box
}
