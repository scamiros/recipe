package org.lucius.recipe.services;

import org.lucius.recipe.domain.Recipe;
import org.lucius.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> findRecipes() {
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
