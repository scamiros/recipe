package org.lucius.recipe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lucius.recipe.converter.RecipeCommandToRecipe;
import org.lucius.recipe.converter.RecipeToRecipeCommand;
import org.lucius.recipe.domain.Recipe;
import org.lucius.recipe.repositories.RecipeRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeService(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void findRecipes() {

        Recipe recipe = new Recipe();
        List<Recipe> recipesData = new ArrayList<>();
        recipesData.add(recipe);

        when(recipeService.findRecipes()).thenReturn(recipesData);

        List<Recipe> recipes = recipeService.findRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }
}