package org.lucius.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.lucius.recipe.commands.RecipeCommand;
import org.lucius.recipe.converter.RecipeCommandToRecipe;
import org.lucius.recipe.converter.RecipeToRecipeCommand;
import org.lucius.recipe.domain.Recipe;
import org.lucius.recipe.exceptions.NotFoundException;
import org.lucius.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeService(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    public List<Recipe> findRecipes() {

        log.debug("List of Recipes");

        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Recipe not found for id " + id));
    }

    public RecipeCommand findCommandById(Long id) {
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id));
        return recipeCommand;
    }

    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId: {}", savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Transactional
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
