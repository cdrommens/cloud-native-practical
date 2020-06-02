package com.ezgroceries.shoppinglist.web.cocktails.contracts;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 09:43
 */
public class CocktailResource {

    private final UUID cocktailId;
    private final String name;
    private final String glass;
    private final String instructions;
    private final String image;
    private final List<String> ingredients;

    public CocktailResource(UUID cocktailId, String name, String glass, String instructions, String image, List<String> ingredients) {
        this.cocktailId = cocktailId;
        this.name = name;
        this.glass = glass;
        this.instructions = instructions;
        this.image = image;
        this.ingredients = ingredients;
    }

    public UUID getCocktailId() {
        return cocktailId;
    }

    public String getName() {
        return name;
    }

    public String getGlass() {
        return glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImage() {
        return image;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CocktailResource)) {
            return false;
        }
        CocktailResource that = (CocktailResource) o;
        return Objects.equals(getCocktailId(), that.getCocktailId()) && Objects.equals(getName(), that.getName()) &&
            Objects.equals(getGlass(), that.getGlass()) && Objects.equals(getInstructions(), that.getInstructions()) &&
            Objects.equals(getImage(), that.getImage()) && Objects.equals(getIngredients(), that.getIngredients());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCocktailId(), getName(), getGlass(), getInstructions(), getImage(), getIngredients());
    }

    @Override
    public String toString() {
        return "CocktailResource{" + "cocktailId=" + cocktailId + ", name='" + name + '\'' + ", glass='" + glass + '\'' + ", instructions='" + instructions +
            '\'' + ", image='" + image + '\'' + ", ingredients=" + ingredients + '}';
    }
}
