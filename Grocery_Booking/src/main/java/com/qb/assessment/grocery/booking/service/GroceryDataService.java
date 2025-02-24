package com.qb.assessment.grocery.booking.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.qb.assessment.grocery.booking.dto.GroceryDetailsResponseDto;
import com.qb.assessment.grocery.booking.dto.GroceryItemRequestDto;

public interface GroceryDataService {

	public List<GroceryDetailsResponseDto> fetchAllGroceryDetails(GroceryItemRequestDto groceryItemRequestDto) throws NotFoundException;
	

}
