package com.ezgroceries.shoppinglist.web.shoppinglist.contracts;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * User : cederik
 * Date : 09/06/2020
 * Time : 07:48
 */
public class CreateShoppingListRequest {

    private final String name;

    @JsonCreator
    public CreateShoppingListRequest(String name) {
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
        if (!(o instanceof CreateShoppingListRequest)) {
            return false;
        }
        CreateShoppingListRequest resource = (CreateShoppingListRequest) o;
        return Objects.equals(getName(), resource.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
