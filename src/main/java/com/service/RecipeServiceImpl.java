package com.service;

import com.model.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepo;

    RecipeServiceImpl(RecipeRepository recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @Override
    public Recipe getRecipeById(int id) {
        Optional<Recipe> getRecipe = recipeRepo.findById(id);
        if (getRecipe.isPresent())
            return getRecipe.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Recipe> getRecipesByName(String name) {
        return recipeRepo.findAllByNameIsContainingIgnoreCaseOrderByDateDesc(name);
    }

    @Override
    public List<Recipe> getRecipesByCat(String category) {
        return recipeRepo.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    @Override
    public Recipe insert(Recipe recipe) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        recipe.setDate(LocalDateTime.now());
        recipe.setOwner(auth.getName());
        return recipeRepo.save(recipe);
    }

    @Override
    public Recipe updateRecipe(Recipe recipe, int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Recipe toUpdateRecipe = getRecipeById(id);
        if (!auth.getName().equals(toUpdateRecipe.getOwner()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        toUpdateRecipe.setDate(LocalDateTime.now());
        toUpdateRecipe.setCategory(recipe.getCategory());
        toUpdateRecipe.setName(recipe.getName());
        toUpdateRecipe.setDescription(recipe.getDescription());
        toUpdateRecipe.setIngredients(recipe.getIngredients());
        toUpdateRecipe.setDirections(recipe.getDirections());
        return insert(toUpdateRecipe);
    }

    @Override
    public Recipe deleteRecipe(int recipeId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Recipe tmpRecipe = getRecipeById(recipeId);
        if (!tmpRecipe.getOwner().equals(auth.getName()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        recipeRepo.deleteById(recipeId);
        return tmpRecipe;
    }
}