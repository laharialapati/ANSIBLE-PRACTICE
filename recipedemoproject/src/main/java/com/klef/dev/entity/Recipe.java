package com.klef.dev.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(length = 1000)
    private String description;

    @Column(length = 2000)
    private String ingredients;

    @Column(length = 2000)
    private String steps;

    public Recipe() {}

    public Recipe(String name, String description, String ingredients, String steps) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
