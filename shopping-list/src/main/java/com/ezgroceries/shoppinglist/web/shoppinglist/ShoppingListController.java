package com.ezgroceries.shoppinglist.web.shoppinglist;

import com.ezgroceries.shoppinglist.services.ShoppingListService;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.AddCocktailToShoppingListResource;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.CreateShoppingListRequest;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.ShoppingListCreatedResponse;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.ShoppingListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingListCreatedResponse createShoppingList(@RequestBody CreateShoppingListRequest createShoppingListResource) {
        Objects.requireNonNull(createShoppingListResource);
        return shoppingListService.create(createShoppingListResource);
    }

    @PostMapping("/{shoppingListId}/cocktails")
    public List<AddCocktailToShoppingListResource> addCocktailToShoppingList(@PathVariable("shoppingListId") UUID shoppingListId,
                                                                             @RequestBody List<AddCocktailToShoppingListResource> addCocktails) {
        Objects.requireNonNull(shoppingListId);
        Objects.requireNonNull(addCocktails);
        shoppingListService.addCocktailToShoppingList(shoppingListId, addCocktails);
        return addCocktails;
    }

    @GetMapping("/{shoppingListId}")
    public ShoppingListResponse getShoppingList(@PathVariable("shoppingListId") UUID shoppingListId) {
        Objects.requireNonNull(shoppingListId);
        return shoppingListService.getShoppingList(shoppingListId);
    }

    @GetMapping
    public List<ShoppingListResponse> getAllShoppingList() {
        return shoppingListService.getAllShoppingLists();
    }
}
