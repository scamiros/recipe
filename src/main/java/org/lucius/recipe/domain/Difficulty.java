package org.lucius.recipe.domain;

public enum Difficulty {

    EASY("Easy"),
    MODERATE("Moderate"),
    KIND_OF_HARD("Kind of Hard"),
    HARD("Hard");

    private String description;

    Difficulty(String desc) {
        description = desc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
