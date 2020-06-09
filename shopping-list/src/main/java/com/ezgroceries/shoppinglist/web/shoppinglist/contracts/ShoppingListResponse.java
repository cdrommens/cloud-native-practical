package com.ezgroceries.shoppinglist.web.shoppinglist.contracts;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 10:03
 */
public class ShoppingListResponse {

    private final UUID shoppingListId;
    private final String name;
    private final List<String> ingredients;

    public ShoppingListResponse(UUID shoppingListId, String name, List<String> ingredients) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.ingredients = ingredients;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingListResponse)) {
            return false;
        }
        ShoppingListResponse that = (ShoppingListResponse) o;
        return Objects.equals(getShoppingListId(), that.getShoppingListId()) && Objects.equals(getName(), that.getName()) &&
            Objects.equals(getIngredients(), that.getIngredients());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShoppingListId(), getName(), getIngredients());
    }

    @Override
    public String toString() {
        return "ShoppingListResource{" + "shoppingListId=" + shoppingListId + ", name='" + name + '\'' + ", ingredients=" + ingredients + '}';
    }
}
