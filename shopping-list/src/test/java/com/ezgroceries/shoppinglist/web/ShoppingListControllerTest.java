package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.services.ShoppingListService;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.AddCocktailToShoppingListResource;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.CreateShoppingListRequest;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.ShoppingListCreatedResponse;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.ShoppingListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 10:07
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("hsqldb")
public class ShoppingListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ShoppingListService shoppingListService;

    @Test
    public void testCreateShoppingList() throws Exception {
        String name = "Stephanie's birthday";
        CreateShoppingListRequest input = new CreateShoppingListRequest(name);

        ShoppingListCreatedResponse expected = new ShoppingListCreatedResponse(UUID.randomUUID(), name);
        given(shoppingListService.create(any())).willReturn(expected);

        this.mockMvc.perform(post("/shopping-lists").content(mapper.writeValueAsString(input))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(expected)));

        then(shoppingListService).should().create(input);
    }

    @Test
    public void testAddCocktailToShoppingList() throws Exception {
        final String shoppingListId = "97c8e5bd-5353-426e-b57b-69eb2260ace3";

        List<AddCocktailToShoppingListResource> addCocktails = Arrays.asList(
            new AddCocktailToShoppingListResource(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4")),
            new AddCocktailToShoppingListResource(UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073")));

        String content = mapper.writeValueAsString(addCocktails);

        this.mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails", shoppingListId).
            content(content).contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(content));

        then(shoppingListService).should().addCocktailToShoppingList(UUID.fromString(shoppingListId), addCocktails);
    }

    @Test
    public void testGetShoppingList() throws Exception {
        final String shoppingListId = "97c8e5bd-5353-426e-b57b-69eb2260ace3";
        ShoppingListResponse expected = new ShoppingListResponse(UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"),
            "Stephanie's birthday",
            Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt","Blue Curacao"));

        given(shoppingListService.getShoppingList(UUID.fromString(shoppingListId))).willReturn(expected);

        this.mockMvc.perform(get("/shopping-lists/{shoppingListId}", shoppingListId))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(expected)));

        then(shoppingListService).should().getShoppingList(UUID.fromString(shoppingListId));
    }

    @Test
    public void testGetAllShoppingList() throws Exception {
        List<ShoppingListResponse> expected = Arrays.asList(
            new ShoppingListResponse(UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"), "Stephanie's birthday",
                Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt","Blue Curacao")),
            new ShoppingListResponse(UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"), "My Birthday",
                Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt","Blue Curacao")));

        given(shoppingListService.getAllShoppingLists()).willReturn(expected);

        this.mockMvc.perform(get("/shopping-lists"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(expected)));

        then(shoppingListService).should().getAllShoppingLists();
    }
}
