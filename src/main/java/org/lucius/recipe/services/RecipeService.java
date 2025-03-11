package org.lucius.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.lucius.recipe.domain.Recipe;
import org.lucius.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> findRecipes() {

        log.debug("List of Recipes");

        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found"));
    }
}
