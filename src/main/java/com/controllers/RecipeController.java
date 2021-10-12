package com.controllers;

import com.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.service.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/recipe")
@RestController
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(@Autowired RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(value = "/search")
    public List<Recipe> getRecipesByParameter(@RequestParam Optional<String> category, @RequestParam Optional<String> name) {
        if (category.isEmpty() && name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "need parameter");
        } else if (category.isPresent() && name.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "only one param");
        } else
            return category.map(s -> recipeService.getRecipesByCat(s))
                    .orElseGet(() -> recipeService.getRecipesByName(name.get()));
    }

    @GetMapping(value = "/{recipeId}")
    public Recipe getRecipe(@PathVariable("recipeId") int recipeId) {
        return recipeService.getRecipeById(recipeId);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Map<String, Integer>> newRecipe(@Valid @RequestBody Recipe recipe) {
        recipeService.insert(recipe);
        return new ResponseEntity<>(Map.of("id", recipe.getId()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") int id, @Valid @RequestBody Recipe recipe) {
        Recipe tmpRecipe = recipeService.getRecipeById(id);
        recipeService.updateRecipe(recipe, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{recipeId}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable("recipeId") int recipeId) {
        Recipe tmpRecipe = recipeService.deleteRecipe(recipeId);
        return new ResponseEntity<>(tmpRecipe, HttpStatus.NO_CONTENT);
    }


}