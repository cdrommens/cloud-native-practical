package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.services.external.cocktailDb.CocktailDBClient;
import com.ezgroceries.shoppinglist.services.external.cocktailDb.contracts.CocktailDBResponse;
import com.ezgroceries.shoppinglist.repositories.models.CocktailEntity;
import com.ezgroceries.shoppinglist.repositories.CocktailRepository;
import com.ezgroceries.shoppinglist.services.external.cocktailDb.helper.DrinkResourceToCocktailResourceConverter;
import com.ezgroceries.shoppinglist.web.cocktails.contracts.CocktailResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 12:19
 */
@Service
@Transactional
public class CocktailService {

    private final CocktailDBClient cocktailDBClient;
    private final CocktailRepository cocktailRepository;

    public CocktailService(CocktailDBClient cocktailDBClient, CocktailRepository cocktailRepository) {
        this.cocktailDBClient = cocktailDBClient;
        this.cocktailRepository = cocktailRepository;
    }

    public List<CocktailResponse> searchCocktails(String search) {
        CocktailDBResponse cocktailDBResponse = cocktailDBClient.searchCocktails(search);
        return mergeCocktails(cocktailDBResponse.getDrinks());
    }

    private List<CocktailResponse> mergeCocktails(List<CocktailDBResponse.DrinkResource> drinks) {
        //Get all the idDrink attributes
        List<String> ids = drinks.stream().map(CocktailDBResponse.DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, CocktailEntity>
            existingEntityMap = cocktailRepository.findByIdDrinkIn(ids).stream()
            .collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, CocktailEntity> allEntityMap = drinks.stream().map(drinkResource -> {
            CocktailEntity cocktailEntity = existingEntityMap.get(drinkResource.getIdDrink());
            if (cocktailEntity == null) {
                DrinkResourceToCocktailResourceConverter converter = new DrinkResourceToCocktailResourceConverter();
                CocktailResponse cocktailResource = converter.convert(drinkResource);

                CocktailEntity newCocktailEntity = new CocktailEntity();
                newCocktailEntity.setId(UUID.randomUUID());
                newCocktailEntity.setIdDrink(drinkResource.getIdDrink());
                newCocktailEntity.setName(drinkResource.getStrDrink());
                newCocktailEntity.setIngredients(new HashSet<>(cocktailResource.getIngredients()));
                cocktailEntity = cocktailRepository.save(newCocktailEntity);
            }
            return cocktailEntity;
        }).collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Merge drinks and our entities, transform to CocktailResource instances
        return mergeAndTransform(drinks, allEntityMap);
    }

    private List<CocktailResponse> mergeAndTransform(List<CocktailDBResponse.DrinkResource> drinks, Map<String, CocktailEntity> allEntityMap) {
        return drinks.stream().map(drinkResource -> {
            DrinkResourceToCocktailResourceConverter converter = new DrinkResourceToCocktailResourceConverter();
            return converter.convert(drinkResource, allEntityMap.get(drinkResource.getIdDrink()).getId());
        }).collect(Collectors.toList());
    }


}
