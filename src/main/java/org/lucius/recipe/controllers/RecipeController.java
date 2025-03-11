package org.lucius.recipe.controllers;

import org.lucius.recipe.domain.Recipe;
import org.lucius.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable Long id, Model model) {

        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);

        return "/recipe/show";
    }
}
