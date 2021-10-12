package com.service;

import com.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipesByCat(String category);

    List<Recipe> getRecipesByName(String name);

    Recipe getRecipeById(int id);

    Recipe insert(Recipe recipe);

    Recipe updateRecipe(Recipe recipe, int id);

    Recipe deleteRecipe(int recipeId);
}