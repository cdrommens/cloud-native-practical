package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.repositories.models.CocktailEntity;
import com.ezgroceries.shoppinglist.repositories.models.ShoppingListEntity;
import com.ezgroceries.shoppinglist.repositories.CocktailRepository;
import com.ezgroceries.shoppinglist.repositories.ShoppingListRepository;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.AddCocktailToShoppingListResource;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.CreateShoppingListRequest;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.ShoppingListCreatedResponse;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.ShoppingListResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
    public ShoppingListCreatedResponse create(CreateShoppingListRequest createShoppingListResource) {
        ShoppingListEntity entity = new ShoppingListEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(createShoppingListResource.getName());
        shoppingListRepository.save(entity);
        return new ShoppingListCreatedResponse(entity.getId(), entity.getName());
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
        shoppingListRepository.save(shoppingListEntity);
    }

    @Transactional(readOnly = true)
    public ShoppingListResponse getShoppingList(UUID shoppingListId) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findById(shoppingListId)
            .orElseThrow(() -> new IllegalArgumentException("Shopping list does not exist"));

        return processSingleShoppingList(shoppingListEntity);
    }

    @Transactional(readOnly = true)
    public List<ShoppingListResponse> getAllShoppingLists() {
        List<ShoppingListEntity> shoppingListEntities = shoppingListRepository.findAll();

        if (CollectionUtils.isEmpty(shoppingListEntities)) {
            return Collections.emptyList();
        }

        List<ShoppingListResponse> resources = new ArrayList<>();
        for (ShoppingListEntity shoppingListEntity : shoppingListEntities) {
            resources.add(processSingleShoppingList(shoppingListEntity));
        }
        return resources;
    }

    private ShoppingListResponse processSingleShoppingList(ShoppingListEntity shoppingListEntity) {
        if (CollectionUtils.isEmpty(shoppingListEntity.getCocktails())) {
            return new ShoppingListResponse(shoppingListEntity.getId(), shoppingListEntity.getName(), Collections.emptyList());
        }

        List<String> ingredients = shoppingListEntity.getCocktails().stream()
            .map(CocktailEntity::getIngredients)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        return new ShoppingListResponse(shoppingListEntity.getId(), shoppingListEntity.getName(), ingredients);
    }

}
