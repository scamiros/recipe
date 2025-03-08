package org.lucius.recipe.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lucius.recipe.domain.Recipe;
import org.lucius.recipe.services.RecipeService;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController indexController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    void getIndexPage() {

        // given
        List<Recipe> recipesData = new ArrayList<>();
        recipesData.add(new Recipe());
        recipesData.add(new Recipe());

        when(recipeService.findRecipes()).thenReturn(recipesData);

        ArgumentCaptor<List<Recipe>> captor = ArgumentCaptor.forClass(List.class);

        // when
        String indexPage = indexController.getIndexPage(model);

        // then
        assertEquals(indexPage, "index");

        verify(recipeService, times(1)).findRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), captor.capture());
        List<Recipe> listCaptor = captor.getValue();
        assertEquals(2, listCaptor.size());

    }
}