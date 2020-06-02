package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.web.shoppinglist.ShoppingListController;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.AddCocktailToShoppingListResource;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.CreateShoppingListResource;
import com.ezgroceries.shoppinglist.web.shoppinglist.contracts.ShoppingListResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    @Test
    public void testCreateShoppingList() throws Exception {
        String name = "Stephanie's birthday";

        this.mockMvc.perform(post("/shopping-lists").content(name))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            //.andExpect(content().json(expected));
            .andExpect(jsonPath("shoppingListId").isNotEmpty())
            .andExpect(jsonPath("name").value(name));
    }

    @Test
    public void testAddCocktailToShoppingList() throws Exception {
        List<AddCocktailToShoppingListResource> addCocktails = Arrays.asList(
            new AddCocktailToShoppingListResource(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4")),
            new AddCocktailToShoppingListResource(UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073")));

        String content = mapper.writeValueAsString(addCocktails);

        this.mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails", "97c8e5bd-5353-426e-b57b-69eb2260ace3").
            content(content).contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(content));
    }

    @Test
    public void testGetShoppingList() throws Exception {
        String expected = mapper.writeValueAsString(getShoppingListDummy().get(0));

        this.mockMvc.perform(get("/shopping-lists/{shoppingListId}", "97c8e5bd-5353-426e-b57b-69eb2260ace3"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expected));
    }

    @Test
    public void testGetAllShoppingList() throws Exception {
        String expected = mapper.writeValueAsString(getShoppingListDummy());

        this.mockMvc.perform(get("/shopping-lists"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expected));
    }

    private List<ShoppingListResource> getShoppingListDummy() {
        return Arrays.asList(
            new ShoppingListResource(UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"), "Stephanie's birthday",
                Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt","Blue Curacao")),
            new ShoppingListResource(UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"), "My Birthday",
                Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt","Blue Curacao")));
    }
}
