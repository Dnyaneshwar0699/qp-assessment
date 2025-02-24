package com.qb.assessment.grocery.booking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import com.qb.assessment.grocery.booking.dto.GroceryDetailsResponseDto;
import com.qb.assessment.grocery.booking.dto.GroceryItemRequestDto;
import com.qb.assessment.grocery.booking.entity.GroceryItemDetails;
import com.qb.assessment.grocery.booking.repository.GroceryItemRepository;
import com.qb.assessment.grocery.booking.service.GroceryDataService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GroceryDataServiceImpl implements GroceryDataService {

    @Autowired
    private GroceryItemRepository groceryItemRepository;
    List<GroceryItemDetails> groceryItemDetails;

    @Override
    @Transactional
    public List<GroceryDetailsResponseDto> fetchAllGroceryDetails(GroceryItemRequestDto groceryItemRequestDto) {
        System.out.println("Fetching all grocery details");
        
        try {
            String itemName = groceryItemRequestDto.getItemName();
            List<GroceryItemDetails> groceryItems = groceryItemRepository.findByName(itemName);
            
            return groceryItems.stream()
                    .map(this::convertToResponseDto)
                    .collect(Collectors.toList());
        } catch (DataAccessResourceFailureException e) {
            throw new DataAccessResourceFailureException("Database access error", e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid request parameters", e);
        }
    }

    private GroceryDetailsResponseDto convertToResponseDto(GroceryItemDetails groceryItemDetails) {
        GroceryDetailsResponseDto dto = new GroceryDetailsResponseDto();
        dto.setItemId(groceryItemDetails.getItemId());
        dto.setItemName(groceryItemDetails.getItemName());
        dto.setPrice(groceryItemDetails.getPrice());
        dto.setItemQuantity(groceryItemDetails.getItemQuantity());
        return dto;
    }
}
