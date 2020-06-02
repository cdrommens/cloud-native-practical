package com.ezgroceries.shoppinglist.web.shoppinglist.contracts;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;
import java.util.UUID;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 10:16
 */
public class AddCocktailToShoppingListResource {

    private final UUID cocktailId;

    @JsonCreator
    public AddCocktailToShoppingListResource(UUID cocktailId) {
        this.cocktailId = cocktailId;
    }

    public UUID getCocktailId() {
        return cocktailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddCocktailToShoppingListResource)) {
            return false;
        }
        AddCocktailToShoppingListResource that = (AddCocktailToShoppingListResource) o;
        return Objects.equals(getCocktailId(), that.getCocktailId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCocktailId());
    }

    @Override
    public String toString() {
        return "AddCocktailToShoppingListResource{" + "cocktailId=" + cocktailId + '}';
    }
}
