package org.lucius.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.lucius.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index", "index.html"})
    public String getIndexPage(Model model) {

        log.debug("Adding recipes to model");
        model.addAttribute("recipes", recipeService.findRecipes());
        return "index";
    }
}
