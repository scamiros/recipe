package org.lucius.recipe.commands;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.lucius.recipe.domain.Difficulty;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(value = 1)
    @Max(value = 999)
    private Integer prepTime;

    @Min(value = 1)
    @Max(value = 999)
    private Integer cookTime;

    @Min(value = 1)
    @Max(value = 100)
    private Integer servings;
    private String source;

    @URL
    private String url;
    private Byte[] image;

    @NotBlank
    private String directions;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();
    private MultipartFile file;
}