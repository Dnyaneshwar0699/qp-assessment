package com.qb.assessment.grocery.booking.service;

import java.util.List;

import com.qb.assessment.grocery.booking.dto.GroceryAdminItemRequestDto;
import com.qb.assessment.grocery.booking.dto.GroceryDetailsResponseDto;

public interface GroceryAdminService {
	GroceryDetailsResponseDto addGroceryItem(GroceryAdminItemRequestDto groceryItemRequestDto);
    List<GroceryDetailsResponseDto> getAllGroceryItems();
    boolean deleteGroceryItem(Long id);
    GroceryDetailsResponseDto updateGroceryItem(Long id, GroceryAdminItemRequestDto groceryAdminItemRequestDto);
    GroceryDetailsResponseDto updateInventory(Long id, int quantity);
}
