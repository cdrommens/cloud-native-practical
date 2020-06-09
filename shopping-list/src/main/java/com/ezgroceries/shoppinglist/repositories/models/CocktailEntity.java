package com.ezgroceries.shoppinglist.repositories.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 13:32
 */
@Entity
@Table(name = "COCKTAIL")
public class CocktailEntity {

    @Id
    @Column(name = "ID")
    private UUID id;
    @Column(name = "ID_DRINK")
    private String idDrink;
    @Column(name = "NAME")
    private String name;
    @Convert(converter = StringSetConverter.class)
    @Column(name = "INGREDIENTS")
    private Set<String> ingredients;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "COCKTAIL_SHOPPING_LIST",
        joinColumns = { @JoinColumn(name = "COCKTAIL_ID")},
        inverseJoinColumns = { @JoinColumn(name = "SHOPPING_LIST_ID")}
    )
    private Set<ShoppingListEntity> shoppingLists = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<ShoppingListEntity> getShoppingLists() {
        return shoppingLists;
    }

    public void setShoppingLists(Set<ShoppingListEntity> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    @Transient
    public void addCocktailToShoppingList(ShoppingListEntity shoppingListEntity) {
        shoppingListEntity.getCocktails().add(this);
        this.shoppingLists.add(shoppingListEntity);
    }

}
