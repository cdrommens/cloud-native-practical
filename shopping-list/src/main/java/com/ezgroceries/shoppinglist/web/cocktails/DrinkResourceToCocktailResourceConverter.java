package com.ezgroceries.shoppinglist.web.cocktails;

import com.ezgroceries.shoppinglist.connectivity.coctailDb.contracts.CocktailDBResponse;
import com.ezgroceries.shoppinglist.web.cocktails.contracts.CocktailResource;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 11:19
 */
public class DrinkResourceToCocktailResourceConverter implements Converter<CocktailDBResponse.DrinkResource, CocktailResource> {

    @Override
    public CocktailResource convert(CocktailDBResponse.DrinkResource drinkResource) {
        List<String> ingredients = new ArrayList<>();
        addIngredient(ingredients, drinkResource.getStrIngredient1());
        addIngredient(ingredients, drinkResource.getStrIngredient2());
        addIngredient(ingredients, drinkResource.getStrIngredient3());
        addIngredient(ingredients, drinkResource.getStrIngredient4());
        addIngredient(ingredients, drinkResource.getStrIngredient5());
        addIngredient(ingredients, drinkResource.getStrIngredient6());
        addIngredient(ingredients, drinkResource.getStrIngredient7());
        addIngredient(ingredients, drinkResource.getStrIngredient8());
        addIngredient(ingredients, drinkResource.getStrIngredient9());
        addIngredient(ingredients, drinkResource.getStrIngredient10());
        addIngredient(ingredients, drinkResource.getStrIngredient11());
        addIngredient(ingredients, drinkResource.getStrIngredient12());
        addIngredient(ingredients, drinkResource.getStrIngredient13());
        addIngredient(ingredients, drinkResource.getStrIngredient14());
        addIngredient(ingredients, drinkResource.getStrIngredient15());
        return new CocktailResource(
            UUID.randomUUID(),
            drinkResource.getStrDrink(),
            drinkResource.getStrGlass(),
            drinkResource.getStrInstructions(),
            drinkResource.getStrDrinkThumb(),
            ingredients
        );
    }

    private void addIngredient(List<String> ingredients, String ingredient) {
        if (ingredient != null) {
            ingredients.add(ingredient);
        }
    }
}
