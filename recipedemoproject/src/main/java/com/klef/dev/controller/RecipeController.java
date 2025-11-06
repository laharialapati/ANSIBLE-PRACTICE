package com.klef.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.klef.dev.entity.Recipe;
import com.klef.dev.service.RecipeService;

@CrossOrigin(origins = "*")  // enable CORS for development
@RestController
@RequestMapping("/recipeapi")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Recipe Management System - Full Stack Deployment");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRecipe(@RequestBody Recipe recipe) {
        try {
            Recipe savedRecipe = recipeService.addRecipe(recipe);
            return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding recipe: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable int id) {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe != null) {
            return ResponseEntity.ok(recipe);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Recipe with ID " + id + " not found.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRecipe(@RequestBody Recipe recipe) {
        Recipe existing = recipeService.getRecipeById(recipe.getId());
        if (existing != null) {
            Recipe updatedRecipe = recipeService.updateRecipe(recipe);
            return ResponseEntity.ok(updatedRecipe);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Cannot update. Recipe with ID " + recipe.getId() + " not found.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int id) {
        Recipe existing = recipeService.getRecipeById(id);
        if (existing != null) {
            recipeService.deleteRecipeById(id);
            return ResponseEntity.ok("Recipe with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Cannot delete. Recipe with ID " + id + " not found.");
        }
    }
}
