package org.lucius.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.lucius.recipe.commands.IngredientCommand;
import org.lucius.recipe.converter.IngredientCommandToIngredient;
import org.lucius.recipe.converter.IngredientToIngredientCommand;
import org.lucius.recipe.domain.Ingredient;
import org.lucius.recipe.domain.Recipe;
import org.lucius.recipe.repositories.RecipeRepository;
import org.lucius.recipe.repositories.UnitOfMesaureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMesaureRepository unitOfMesaureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientService(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, UnitOfMesaureRepository unitOfMesaureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMesaureRepository = unitOfMesaureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);

        // TODO implemente errors manage
        if (!recipe.isPresent()) log.error("Recipe not found");

        Optional<IngredientCommand> ing = recipe.get().getIngredients().stream().filter(i -> i.getId().equals(id))
                .map(i -> ingredientToIngredientCommand.convert(i)).findFirst();

        return ing.get();

    }

    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            // TODO implemente errors manage
            log.error("Recipe not found");

            return new IngredientCommand();
        } else {

            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingOptional = recipeOptional.get().getIngredients()
                    .stream()
                    .filter(i -> i.getId().equals(command.getId()))
                    .findFirst();

            if (ingOptional.isPresent()) {
                Ingredient ing = ingOptional.get();
                ing.setDescription(command.getDescription());
                ing.setAmount(command.getAmount());
                ing.setUom(unitOfMesaureRepository.findById(command.getUom().getId()).orElseThrow(() -> new RuntimeException("Uom nont found with id " + command.getUom().getId())));
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.getIngredients().add(ingredient);
            }

            recipeRepository.save(recipe);

            Ingredient savedIngredient = recipe.getIngredients().stream()
                    .filter(i -> i.getDescription().equals(command.getDescription()))
                    .filter(i -> i.getAmount().equals(command.getAmount()))
                    .filter(i -> i.getUom().getId().equals(command.getUom().getId())).findFirst().get();
            return ingredientToIngredientCommand.convert(savedIngredient);
        }
    }

    @Transactional
    public void deleteById(Long recipeId, Long id) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            // TODO implemente errors manage
            log.error("Recipe not found");
        } else {

            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingOptional = recipeOptional.get().getIngredients()
                    .stream()
                    .filter(i -> i.getId().equals(id))
                    .findFirst();

            if (ingOptional.isPresent()) {
                Ingredient ing = ingOptional.get();
                ing.setRecipe(null);
                recipe.getIngredients().remove(ing);
                recipeRepository.save(recipe);

            }
        }
    }
}
