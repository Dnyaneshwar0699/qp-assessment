package com.qb.assessment.grocery.booking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.qb.assessment.grocery.booking.entity.GroceryItemDetails;

import jakarta.transaction.Transactional;

@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItemDetails, Long> {
    
    @Query("SELECT groceryItemDetails FROM GroceryItemDetails groceryItemDetails WHERE groceryItemDetails.itemName = :itemName")
    List<GroceryItemDetails> findByName(@Param("itemName") String itemName);
    
    @Modifying
    @Transactional
    @Query("UPDATE GroceryItemDetails groceryItemDetails SET groceryItemDetails.itemQuantity = groceryItemDetails.itemQuantity - :quantity WHERE groceryItemDetails.itemId = :itemId AND groceryItemDetails.itemQuantity >= :quantity")
    int updateStockQuantity(@Param("itemId") Long itemId, @Param("quantity") int quantity);

    @Query("SELECT groceryItemDetails FROM GroceryItemDetails groceryItemDetails WHERE groceryItemDetails.itemId = :itemId")
    Optional<GroceryItemDetails> findGroceryItemById(@Param("itemId") Long itemId);
    
//    @Query("SELECT groceryItemDetails FROM GroceryItemDetails groceryItemDetails")
//    List<GroceryItemDetails> findAllGroceryItems();
}

