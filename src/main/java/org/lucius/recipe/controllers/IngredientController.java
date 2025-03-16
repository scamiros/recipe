package org.lucius.recipe.controllers;

import org.lucius.recipe.commands.IngredientCommand;
import org.lucius.recipe.commands.RecipeCommand;
import org.lucius.recipe.commands.UnitOfMeasureCommand;
import org.lucius.recipe.services.IngredientService;
import org.lucius.recipe.services.RecipeService;
import org.lucius.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipe")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredients")
    public String showRecipeIngredients(@PathVariable Long recipeId, Model model) {

        RecipeCommand recipe = recipeService.findCommandById(recipeId);
        model.addAttribute("recipe", recipe);

        return "/recipe/ingredients/list";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long id, Model model) {

        IngredientCommand ingredient = ingredientService.findByRecipeIdAndIngredientId(recipeId, id);
        model.addAttribute("ingredient", ingredient);

        return "/recipe/ingredients/show";
    }

    @RequestMapping("/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable Long recipeId, Model model) {

        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        // TODO thwow ex if null
        IngredientCommand ing = new IngredientCommand();
        ing.setRecipeId(recipeCommand.getId());

        ing.setUom(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ing);
        model.addAttribute("listUom", unitOfMeasureService.findAllUoms());

        return "recipe/ingredients/ingredientform";
    }

    @RequestMapping("/{recipeId}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable Long recipeId, @PathVariable Long id, Model model){

        IngredientCommand command = ingredientService.findByRecipeIdAndIngredientId(recipeId, id);
        model.addAttribute("ingredient", command);
        model.addAttribute("listUom", unitOfMeasureService.findAllUoms());

        return "recipe/ingredients/ingredientform";
    }

    @PostMapping("/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/" + + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show" ;
    }

    @RequestMapping("/{recipeId}/ingredient/{id}/delete")
    public String deleteRecipe(@PathVariable Long recipeId, @PathVariable Long id) {

        ingredientService.deleteById(recipeId, id);
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
