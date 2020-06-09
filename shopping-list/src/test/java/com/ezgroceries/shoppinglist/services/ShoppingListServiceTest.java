package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.repositories.models.CocktailEntity;
import com.ezgroceries.shoppinglist.repositories.models.ShoppingListEntity;
import com.ezgroceries.shoppinglist.repositories.CocktailRepository;
import com.ezgroceries.shoppinglist.repositories.ShoppingListRepository;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.AddCocktailToShoppingListResource;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.CreateShoppingListRequest;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.ShoppingListCreatedResponse;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.ShoppingListResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * User : cederik
 * Date : 09/06/2020
 * Time : 08:32
 */
@ExtendWith(MockitoExtension.class)
public class ShoppingListServiceTest {

    @Mock
    private ShoppingListRepository shoppingListRepository;

    @Mock
    private CocktailRepository cocktailRepository;

    @InjectMocks
    private ShoppingListService shoppingListService;

    @Test
    public void testCreate() {
        String name = "Stephanie's birthday";
        CreateShoppingListRequest input = new CreateShoppingListRequest(name);

        ShoppingListCreatedResponse expected = new ShoppingListCreatedResponse(UUID.randomUUID(), name);

        ShoppingListCreatedResponse result = shoppingListService.create(input);
        assertEquals(expected.getName(), result.getName());
        then(shoppingListRepository).should().save(any());
    }

    @Test
    public void testAddCocktailToShoppingList() {
        final String shoppingListId = "97c8e5bd-5353-426e-b57b-69eb2260ace3";
        final String cocktailId = "23b3d85a-3928-41c0-a533-6538a71e17c4";

        List<AddCocktailToShoppingListResource> addCocktails = Arrays.asList(
            new AddCocktailToShoppingListResource(UUID.fromString(cocktailId)));

        CocktailEntity cocktailEntity = new CocktailEntity();
        cocktailEntity.setId(UUID.fromString(cocktailId));
        cocktailEntity.setName("drink1");
        cocktailEntity.setIdDrink("drink1");
        cocktailEntity.setIngredients(new HashSet<>(Arrays.asList("ingr1", "ingr2")));

        ShoppingListEntity shoppingListEntity = new ShoppingListEntity();
        shoppingListEntity.setId(UUID.fromString(shoppingListId));
        shoppingListEntity.setCocktails(new HashSet<>());
        given(shoppingListRepository.findById(UUID.fromString(shoppingListId))).willReturn(Optional.of(shoppingListEntity));
        given(cocktailRepository.findById(UUID.fromString(cocktailId))).willReturn(Optional.of(cocktailEntity));

        shoppingListService.addCocktailToShoppingList(UUID.fromString(shoppingListId), addCocktails);

        then(shoppingListRepository).should().save(shoppingListEntity);
    }

    @Test
    public void testGetShoppingList() {
        final String shoppingListId = "97c8e5bd-5353-426e-b57b-69eb2260ace3";
        final String cocktailId = "23b3d85a-3928-41c0-a533-6538a71e17c4";

        CocktailEntity cocktailEntity = new CocktailEntity();
        cocktailEntity.setId(UUID.fromString(cocktailId));
        cocktailEntity.setName("drink1");
        cocktailEntity.setIdDrink("drink1");
        cocktailEntity.setIngredients(new HashSet<>(Arrays.asList("ingr1", "ingr2")));

        ShoppingListEntity shoppingListEntity = new ShoppingListEntity();
        shoppingListEntity.setId(UUID.fromString(shoppingListId));
        shoppingListEntity.setName("shopping list");
        shoppingListEntity.addCocktailToShoppingList(cocktailEntity);

        given(shoppingListRepository.findById(UUID.fromString(shoppingListId))).willReturn(Optional.of(shoppingListEntity));

        ShoppingListResponse expected = new ShoppingListResponse(UUID.fromString(shoppingListId), "shopping list",
            Arrays.asList("ingr1", "ingr2"));
        ShoppingListResponse result = shoppingListService.getShoppingList(UUID.fromString(shoppingListId));
        assertEquals(expected.getShoppingListId(), result.getShoppingListId());
        assertEquals(expected.getName(), result.getName());
        Collections.sort(expected.getIngredients());
        Collections.sort(result.getIngredients());
        assertEquals(expected.getIngredients(), result.getIngredients());
    }

    @Test
    public void testGetAllShoppingLists() {
        final String shoppingListId1 = "97c8e5bd-5353-426e-b57b-69eb2260ace3";
        final String cocktailId1 = "23b3d85a-3928-41c0-a533-6538a71e17c4";

        CocktailEntity cocktailEntity1 = new CocktailEntity();
        cocktailEntity1.setId(UUID.fromString(cocktailId1));
        cocktailEntity1.setName("drink1");
        cocktailEntity1.setIdDrink("drink1");
        cocktailEntity1.setIngredients(new HashSet<>(Arrays.asList("ingr1", "ingr2")));

        ShoppingListEntity shoppingListEntity1 = new ShoppingListEntity();
        shoppingListEntity1.setId(UUID.fromString(shoppingListId1));
        shoppingListEntity1.setName("shopping list 1");
        shoppingListEntity1.addCocktailToShoppingList(cocktailEntity1);

        final String shoppingListId2 = "97c8e5bd-5353-426e-b57b-69eb2260ace4";
        final String cocktailId2 = "23b3d85a-3928-41c0-a533-6538a71e17c5";

        CocktailEntity cocktailEntity2 = new CocktailEntity();
        cocktailEntity2.setId(UUID.fromString(cocktailId2));
        cocktailEntity2.setName("drink2");
        cocktailEntity2.setIdDrink("drink2");
        cocktailEntity2.setIngredients(new HashSet<>(Arrays.asList("ingr3", "ingr4")));
        CocktailEntity cocktailEntity3 = new CocktailEntity();
        cocktailEntity3.setId(UUID.fromString(cocktailId2));
        cocktailEntity3.setName("drink3");
        cocktailEntity3.setIdDrink("drink3");
        cocktailEntity3.setIngredients(new HashSet<>(Arrays.asList("ingr5", "ingr6")));

        ShoppingListEntity shoppingListEntity2 = new ShoppingListEntity();
        shoppingListEntity2.setId(UUID.fromString(shoppingListId2));
        shoppingListEntity2.setName("shopping list 2");
        shoppingListEntity2.addCocktailToShoppingList(cocktailEntity2);

        given(shoppingListRepository.findAll()).willReturn(Arrays.asList(shoppingListEntity1, shoppingListEntity2));

        List<ShoppingListResponse> expected = Arrays.asList(
            new ShoppingListResponse(UUID.fromString(shoppingListId1), "shopping list 1", Arrays.asList("ingr1", "ingr2")),
            new ShoppingListResponse(UUID.fromString(shoppingListId2), "shopping list 2",
                Arrays.asList("ingr3", "ingr4", "ingr5", "ingr6")));


        List<ShoppingListResponse> result = shoppingListService.getAllShoppingLists();
        result.sort(Comparator.comparing(ShoppingListResponse::getShoppingListId));
        assertEquals(expected.size(), 2);

        assertEquals(expected.get(0).getShoppingListId(), result.get(0).getShoppingListId());
        assertEquals(expected.get(0).getName(), result.get(0).getName());
        Collections.sort(expected.get(0).getIngredients());
        Collections.sort(result.get(0).getIngredients());
        assertEquals(expected.get(0).getIngredients(), result.get(0).getIngredients());

        assertEquals(expected.get(1).getShoppingListId(), result.get(1).getShoppingListId());
        assertEquals(expected.get(1).getName(), result.get(1).getName());
        Collections.sort(expected.get(1).getIngredients());
        Collections.sort(result.get(1).getIngredients());
        assertEquals(expected.get(1).getIngredients(), result.get(1).getIngredients());
    }

}
