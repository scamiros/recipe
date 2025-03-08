package org.lucius.recipe.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lucius.recipe.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CategoryRepositoryITTest {

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByDescription() {
        Optional<Category> category = categoryRepository.findByDescription("Italian");

        assertEquals("Italian", category.get().getDescription());
    }
}