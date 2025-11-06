package com.klef.dev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.dev.entity.Recipe;
import com.klef.dev.repository.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(int id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public Recipe updateRecipe(Recipe recipe) {
        if (recipeRepository.existsById(recipe.getId())) {
            return recipeRepository.save(recipe);
        }
        return null;
    }

    @Override
    public void deleteRecipeById(int id) {
        recipeRepository.deleteById(id);
    }
}
