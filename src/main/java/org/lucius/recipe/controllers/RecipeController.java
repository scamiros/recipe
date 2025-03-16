package org.lucius.recipe.controllers;

import org.lucius.recipe.commands.RecipeCommand;
import org.lucius.recipe.domain.Recipe;
import org.lucius.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/{id}/show")
    public String showById(@PathVariable Long id, Model model) {

        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);

        return "/recipe/show";
    }

    @RequestMapping("/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @PostMapping("/")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + + savedCommand.getId() + "/show" ;
    }

    @RequestMapping("/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model){

        RecipeCommand command = recipeService.findCommandById(id);
        model.addAttribute("recipe", command);

        return "recipe/recipeform";
    }

    @RequestMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id){

        recipeService.deleteById(id);
        return "redirect:/";
    }

}
