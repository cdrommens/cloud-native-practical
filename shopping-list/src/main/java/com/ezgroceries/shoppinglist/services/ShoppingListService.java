package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.models.CocktailEntity;
import com.ezgroceries.shoppinglist.models.ShoppingListEntity;
import com.ezgroceries.shoppinglist.repositories.CocktailRepository;
import com.ezgroceries.shoppinglist.repositories.ShoppingListRepository;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.AddCocktailToShoppingListResource;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.CreateShoppingListResource;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.ShoppingListResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 13:39
 */
@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final CocktailRepository cocktailRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, CocktailRepository cocktailRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailRepository = cocktailRepository;
    }

    @Transactional
    public CreateShoppingListResource create(String name) {
        ShoppingListEntity entity = new ShoppingListEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(name);
        shoppingListRepository.save(entity);
        return new CreateShoppingListResource(entity.getId(), entity.getName());
    }

    @Transactional
    public void addCocktailToShoppingList(UUID shoppingListId, List<AddCocktailToShoppingListResource> addCocktails) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findById(shoppingListId)
            .orElseThrow(() -> new IllegalArgumentException("Shopping list does not exist"));
        if (CollectionUtils.isEmpty(addCocktails)) {
            return;
        }
        for (AddCocktailToShoppingListResource cocktail : addCocktails) {
            CocktailEntity cocktailEntity = cocktailRepository.findById(cocktail.getCocktailId())
                .orElseThrow(() -> new IllegalArgumentException("Cocktail does not exist"));
            shoppingListEntity.addCocktailToShoppingList(cocktailEntity);
        }
    }

    @Transactional(readOnly = true)
    public ShoppingListResource getShoppingList(UUID shoppingListId) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findById(shoppingListId)
            .orElseThrow(() -> new IllegalArgumentException("Shopping list does not exist"));

        return processSingleShoppingList(shoppingListEntity);
    }

    @Transactional(readOnly = true)
    public List<ShoppingListResource> getAllShoppingLists() {
        List<ShoppingListEntity> shoppingListEntities = shoppingListRepository.findAll();

        if (CollectionUtils.isEmpty(shoppingListEntities)) {
            return Collections.emptyList();
        }

        List<ShoppingListResource> resources = new ArrayList<>();
        for (ShoppingListEntity shoppingListEntity : shoppingListEntities) {
            resources.add(processSingleShoppingList(shoppingListEntity));
        }
        return resources;
    }

    private ShoppingListResource processSingleShoppingList(ShoppingListEntity shoppingListEntity) {
        if (CollectionUtils.isEmpty(shoppingListEntity.getCocktails())) {
            return new ShoppingListResource(shoppingListEntity.getId(), shoppingListEntity.getName(), Collections.emptyList());
        }

        List<String> ingredients = shoppingListEntity.getCocktails().stream()
            .map(CocktailEntity::getIngredients)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        return new ShoppingListResource(shoppingListEntity.getId(), shoppingListEntity.getName(), ingredients);
    }

}
