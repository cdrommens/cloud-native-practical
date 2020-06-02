package com.ezgroceries.shoppinglist.repositories;

import com.ezgroceries.shoppinglist.models.CocktailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 13:37
 */
@Repository
public interface CocktailRepository  extends JpaRepository<CocktailEntity, UUID> {
    Set<CocktailEntity> findByIdDrinkIn(List<String> ids);
}
