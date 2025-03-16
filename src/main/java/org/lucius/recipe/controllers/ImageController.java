package org.lucius.recipe.controllers;

import org.lucius.recipe.services.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("recipe/{recipeId}/image")
    public String saveOrUpdateImage(@PathVariable Long recipeId, @RequestParam("file") MultipartFile file) {
        imageService.saveImageFile(recipeId, file);
        return "/recipe/" + recipeId + "/show";
    }
}
