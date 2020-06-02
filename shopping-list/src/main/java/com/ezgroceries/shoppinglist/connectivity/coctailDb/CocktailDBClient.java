package com.ezgroceries.shoppinglist.connectivity.coctailDb;

import com.ezgroceries.shoppinglist.connectivity.coctailDb.contracts.CocktailDBResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 11:12
 */
@Component
@FeignClient(name = "cocktailDBClient", url = "https://www.thecocktaildb.com/api/json/v1/1")
public interface CocktailDBClient {

    @GetMapping(value = "search.php")
    CocktailDBResponse searchCocktails(@RequestParam("s") String search);

}
