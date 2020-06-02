package com.ezgroceries.shoppinglist.web.shoppinglist.contracts;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 10:03
 */
public class CreateShoppingListResource {

    private final UUID shoppingListId;
    private final String name;

    public CreateShoppingListResource(UUID shoppingListId, String name) {
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
        if (!(o instanceof CreateShoppingListResource)) {
            return false;
        }
        CreateShoppingListResource that = (CreateShoppingListResource) o;
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
