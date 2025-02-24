package com.qb.assessment.grocery.booking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qb.assessment.grocery.booking.dto.GroceryAdminItemRequestDto;
import com.qb.assessment.grocery.booking.dto.GroceryDetailsResponseDto;
import com.qb.assessment.grocery.booking.entity.GroceryItemDetails;
import com.qb.assessment.grocery.booking.exception.InsufficientStockException;
import com.qb.assessment.grocery.booking.exception.ItemAlreadyExistsException;
import com.qb.assessment.grocery.booking.exception.ItemNotFoundException;
import com.qb.assessment.grocery.booking.repository.GroceryItemRepository;
import com.qb.assessment.grocery.booking.service.GroceryAdminService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GroceryAdminServiceImpl implements GroceryAdminService {


	    @Autowired
	    private GroceryItemRepository groceryItemRepository;
	    List<GroceryItemDetails> groceryItemDetails;
	    @Override
	    public GroceryDetailsResponseDto addGroceryItem(GroceryAdminItemRequestDto groceryAdminItemRequestDto) {
	        // Check if item with same name exists
	        List<GroceryItemDetails> existingItems = groceryItemRepository.findByName(groceryAdminItemRequestDto.getItemName());
	        if (!existingItems.isEmpty()) {
	            throw new ItemAlreadyExistsException("Item with name " + groceryAdminItemRequestDto.getItemName() + " already exists.");
	        }

	        // Create new grocery item
	        GroceryItemDetails item = new GroceryItemDetails();
	        item.setItemName(groceryAdminItemRequestDto.getItemName());
	        item.setPrice(groceryAdminItemRequestDto.getPrice());
	        item.setItemQuantity(groceryAdminItemRequestDto.getQuantity());

	        GroceryItemDetails savedItem = groceryItemRepository.save(item);
	       // return new GroceryDetailsResponseDto();
	        GroceryDetailsResponseDto responseDto = new GroceryDetailsResponseDto();
	        responseDto.setItemId(item.getItemId());
	        responseDto.setItemName(item.getItemName());
	        responseDto.setPrice(item.getPrice());
	        responseDto.setItemQuantity(item.getItemQuantity());

	        return responseDto;  
	    }

	    @Override
	    public List<GroceryDetailsResponseDto> getAllGroceryItems() {
	        return groceryItemRepository.findAll().stream()
	                .map(item -> {
	                    GroceryDetailsResponseDto groceryDetailsResponseDto = new GroceryDetailsResponseDto();
	                    groceryDetailsResponseDto.setItemId(item.getItemId());
	                    groceryDetailsResponseDto.setItemName(item.getItemName());
	                    groceryDetailsResponseDto.setPrice(item.getPrice());
	                    groceryDetailsResponseDto.setItemQuantity(item.getItemQuantity());
	                    return groceryDetailsResponseDto;
	                })
	                .collect(Collectors.toList());
	    }

	    @Override
	    public boolean deleteGroceryItem(Long id) {
	        GroceryItemDetails item = groceryItemRepository.findGroceryItemById(id)
	                .orElseThrow(() -> new ItemNotFoundException(id , "Item not found with ID: " + id));

	        groceryItemRepository.delete(item);
			return true;
	    }

	    @Override
	    public GroceryDetailsResponseDto updateGroceryItem(Long id, GroceryAdminItemRequestDto groceryAdminItemRequestDto) {
	        GroceryItemDetails item = groceryItemRepository.findGroceryItemById(id)
	                .orElseThrow(() -> new ItemNotFoundException(id, "Item not found with ID: " + id));

	        item.setItemName(groceryAdminItemRequestDto.getItemName());
	        item.setPrice(groceryAdminItemRequestDto.getPrice());
	        item.setItemQuantity(groceryAdminItemRequestDto.getQuantity());

	        GroceryItemDetails updatedItem = groceryItemRepository.save(item);
	        
	        GroceryDetailsResponseDto responseDto = new GroceryDetailsResponseDto();
	        responseDto.setItemId(item.getItemId());
	        responseDto.setItemName(item.getItemName());
	        responseDto.setPrice(item.getPrice());
	        responseDto.setItemQuantity(item.getItemQuantity());

	        return responseDto;  
	      //  return new GroceryDetailsResponseDto();
	    }

	    @Override
	    public GroceryDetailsResponseDto updateInventory(Long id, int newStockQuantity) {
	        GroceryItemDetails groceryItem = groceryItemRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Item with ID " + id + " not found"));

	        groceryItem.setItemQuantity(newStockQuantity);

	        GroceryItemDetails updatedItem = groceryItemRepository.save(groceryItem);

	        GroceryDetailsResponseDto responseDto = new GroceryDetailsResponseDto();
	        responseDto.setItemId(groceryItem.getItemId());
	        responseDto.setItemName(groceryItem.getItemName());
	        responseDto.setPrice(groceryItem.getPrice());
	        responseDto.setItemQuantity(groceryItem.getItemQuantity());

	        return responseDto;
	    }
	
	}
