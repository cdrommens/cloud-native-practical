package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.connectivity.coctailDb.contracts.CocktailDBResponse;
import com.ezgroceries.shoppinglist.web.cocktails.CocktailService;
import com.ezgroceries.shoppinglist.web.cocktails.contracts.CocktailResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 09:50
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CocktailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CocktailService cocktailService;

    @Test
    public void testGetCocktails() throws Exception {
        String searchString = "russian";
        String expected = mapper.writeValueAsString(getDummyResources());
        given(cocktailService.searchCocktails(anyString())).willReturn(getDummyCocktailDBResponse());
        ResultActions resultActions =
            this.mockMvc.perform(get("/cocktails?search={search}", searchString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected));
    }

    private List<CocktailResource> getDummyResources() {
        return Arrays.asList(
            new CocktailResource(
                UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"), "Margerita",
                "Cocktail glass",
                "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt")),
            new CocktailResource(
                UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073"), "Blue Margerita",
                "Cocktail glass",
                "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                Arrays.asList("Tequila", "Blue Curacao", "Lime juice", "Salt")));
    }

    private CocktailDBResponse getDummyCocktailDBResponse() {
        CocktailDBResponse.DrinkResource drinkResource1 = new CocktailDBResponse.DrinkResource();
        drinkResource1.setStrDrink("Margerita");
        drinkResource1.setStrGlass("Cocktail glass");
        drinkResource1.setStrInstructions("Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..");
        drinkResource1.setStrDrinkThumb("https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg");
        drinkResource1.setStrIngredient1("Tequila");
        drinkResource1.setStrIngredient2("Triple sec");
        drinkResource1.setStrIngredient3("Lime juice");
        drinkResource1.setStrIngredient4("Salt");

        CocktailDBResponse.DrinkResource drinkResource2 = new CocktailDBResponse.DrinkResource();
        drinkResource2.setStrDrink("Blue Margerita");
        drinkResource2.setStrGlass("Cocktail glass");
        drinkResource2.setStrInstructions("Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..");
        drinkResource2.setStrDrinkThumb("https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg");
        drinkResource2.setStrIngredient1("Tequila");
        drinkResource2.setStrIngredient2("lue Curacao");
        drinkResource2.setStrIngredient3("Lime juice");
        drinkResource2.setStrIngredient4("Salt");

        CocktailDBResponse response = new CocktailDBResponse();
        response.setDrinks(Arrays.asList(drinkResource1, drinkResource2));
        return response;
    }
}
