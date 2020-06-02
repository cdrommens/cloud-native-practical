package com.ezgroceries.shoppinglist.web.cocktails;

import com.ezgroceries.shoppinglist.connectivity.coctailDb.CocktailDBClient;
import com.ezgroceries.shoppinglist.connectivity.coctailDb.contracts.CocktailDBResponse;
import org.springframework.stereotype.Service;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 12:19
 */
@Service
public class CocktailService {

    private final CocktailDBClient cocktailDBClient;

    public CocktailService(CocktailDBClient cocktailDBClient) {
        this.cocktailDBClient = cocktailDBClient;
    }

    public CocktailDBResponse searchCocktails(String search) {
        return cocktailDBClient.searchCocktails(search);
    }
}
