package com.ezgroceries.shoppinglist.web.shoppinglist.contracts;

import java.util.Objects;
import java.util.UUID;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 10:03
 */
public class ShoppingListCreatedResponse {

    private final UUID shoppingListId;
    private final String name;

    public ShoppingListCreatedResponse(UUID shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingListCreatedResponse)) {
            return false;
        }
        ShoppingListCreatedResponse that = (ShoppingListCreatedResponse) o;
        return Objects.equals(getShoppingListId(), that.getShoppingListId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShoppingListId(), getName());
    }

    @Override
    public String toString() {
        return "ShoppingListResource{" + "shoppingListId=" + shoppingListId + ", name='" + name + '\'' + '}';
    }
}
