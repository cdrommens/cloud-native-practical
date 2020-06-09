package com.ezgroceries.shoppinglist.web.shoppinglist.contracts;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * User : cederik
 * Date : 09/06/2020
 * Time : 07:48
 */
public class CreateShoppingListResource {

    private final String name;

    @JsonCreator
    public CreateShoppingListResource(String name) {
        this.name = name;
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
        CreateShoppingListResource resource = (CreateShoppingListResource) o;
        return Objects.equals(getName(), resource.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
