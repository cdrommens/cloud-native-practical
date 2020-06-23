package com.ezgroceries.shoppinglist.repositories.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 13:35
 */
@Entity
@Table(name = "SHOPPING_LIST")
public class ShoppingListEntity {

    @Id
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "USER_ID")
    private String userId;
    @ManyToMany(mappedBy = "shoppingLists")
    private Set<CocktailEntity> cocktails = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<CocktailEntity> getCocktails() {
        return cocktails;
    }

    public void setCocktails(Set<CocktailEntity> cocktails) {
        this.cocktails = cocktails;
    }

    @Transient
    public void addCocktailToShoppingList(CocktailEntity cocktailEntity) {
        cocktailEntity.getShoppingLists().add(this);
        this.cocktails.add(cocktailEntity);
    }
}
