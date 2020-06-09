package com.ezgroceries.shoppinglist.repositories;

import com.ezgroceries.shoppinglist.repositories.models.ShoppingListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * User : cederik
 * Date : 02/06/2020
 * Time : 13:38
 */
@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, UUID> {
}
