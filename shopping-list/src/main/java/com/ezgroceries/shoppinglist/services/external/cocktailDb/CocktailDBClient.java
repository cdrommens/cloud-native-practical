package com.ezgroceries.shoppinglist.services.external.cocktailDb;

import com.ezgroceries.shoppinglist.repositories.CocktailRepository;
import com.ezgroceries.shoppinglist.repositories.models.CocktailEntity;
import com.ezgroceries.shoppinglist.services.external.cocktailDb.contracts.CocktailDBResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 11:12
 */
@Component
@FeignClient(
    name = "cocktailDBClient",
    url = "https://www.thecocktaildb.com/api/json/v1/1",
    fallback = CocktailDBClient.CocktailDBClientFallback.class)
public interface CocktailDBClient {

    @GetMapping(value = "search.php")
    CocktailDBResponse searchCocktails(@RequestParam("s") String search);

    @Component
    class CocktailDBClientFallback implements CocktailDBClient {

        private final CocktailRepository cocktailRepository;

        public CocktailDBClientFallback(CocktailRepository cocktailRepository) {
            this.cocktailRepository = cocktailRepository;
        }

        @Override
        public CocktailDBResponse searchCocktails(String search) {
            List<CocktailEntity> cocktailEntities = cocktailRepository.findByNameContainingIgnoreCase(search);

            CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
            cocktailDBResponse.setDrinks(cocktailEntities.stream().map(cocktailEntity -> {
                CocktailDBResponse.DrinkResource drinkResource = new CocktailDBResponse.DrinkResource();
                drinkResource.setIdDrink(cocktailEntity.getIdDrink());
                drinkResource.setStrDrink(cocktailEntity.getName());
                drinkResource.setStrGlass(cocktailEntity.getGlass());
                drinkResource.setStrInstructions(cocktailEntity.getInstructions());
                drinkResource.setStrDrinkThumb(cocktailEntity.getImageLink());
                int index = 1;
                for (String ingredient : cocktailEntity.getIngredients()) {
                    setIngredient(drinkResource, index, ingredient);
                }
                return drinkResource;
            }).collect(Collectors.toList()));

            return cocktailDBResponse;
        }

        private void setIngredient(CocktailDBResponse.DrinkResource drinkResource, int index, String ingredient) {
            try {
                Method method = CocktailDBResponse.DrinkResource.class.getMethod("getStrIngredient" + index, String.class);
                method.invoke(drinkResource, ingredient);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
