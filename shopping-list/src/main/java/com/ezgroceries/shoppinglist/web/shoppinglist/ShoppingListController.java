package com.ezgroceries.shoppinglist.web.shoppinglist;

import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.AddCocktailToShoppingListResource;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.CreateShoppingListResource;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.ShoppingListResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 10:01
 */
@RestController
@RequestMapping(value = "/shopping-lists", produces = "application/json")
public class ShoppingListController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateShoppingListResource createShoppingList(@RequestBody String name) {
        Objects.requireNonNull(name);
        return createShoppingListDummy(name);
    }

    @PostMapping("/{shoppingListId}/cocktails")
    public List<AddCocktailToShoppingListResource> addCocktailToShoppingList(@PathVariable("shoppingListId") UUID shoppingListId,
                                                                             @RequestBody List<AddCocktailToShoppingListResource> addCocktails) {
        Objects.requireNonNull(shoppingListId);
        Objects.requireNonNull(addCocktails);
        return addCocktails;
    }

    @GetMapping("/{shoppingListId}")
    public ShoppingListResource getShoppingList(@PathVariable("shoppingListId") UUID shoppingListId) {
        Objects.requireNonNull(shoppingListId);
        return getShoppingListDummy().get(0);
    }

    @GetMapping
    public List<ShoppingListResource> getAllShoppingList() {
        return getShoppingListDummy();
    }

    private CreateShoppingListResource createShoppingListDummy(String name) {
        return new CreateShoppingListResource(UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915"), name);
    }

    private List<ShoppingListResource> getShoppingListDummy() {
        return Arrays.asList(
            new ShoppingListResource(UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"), "Stephanie's birthday",
            Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt","Blue Curacao")),
            new ShoppingListResource(UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"), "My Birthday",
                Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt","Blue Curacao")));
    }
}
