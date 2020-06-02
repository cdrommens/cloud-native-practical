package com.ezgroceries.shoppinglist.web.cocktails;

import com.ezgroceries.shoppinglist.connectivity.coctailDb.contracts.CocktailDBResponse;
import com.ezgroceries.shoppinglist.services.CocktailService;
import com.ezgroceries.shoppinglist.web.cocktails.contracts.CocktailResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 09:41
 */
@RestController
@RequestMapping(value = "/cocktails", produces = "application/json")
public class CocktailController {

    private final CocktailService cocktailService;

    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping
    public List<CocktailResource> get(@RequestParam String search) {
        return cocktailService.searchCocktails(search);
    }

}
