package com.qb.assessment.grocery.booking.exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.qb.assessment.grocery.booking.entity.GroceryItemDetails;
import com.qb.assessment.grocery.booking.repository.GroceryItemRepository;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    // ✅ Handle Item Not Found Exception
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleItemNotFound(ItemNotFoundException ex) {
        List<GroceryItemDetails> availableItems = groceryItemRepository.findAll();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("error", "Item not found with ID: " + ex.getGroceryItemId());
        response.put("message", "Please choose grocery from below list to continue with order:");

        List<Map<String, Object>> availableItemsList = availableItems.stream()
        	    .filter(Objects::nonNull)
        	    .map(item -> {
        	        Map<String, Object> itemMap = new HashMap<>();
        	        itemMap.put("id", item.getItemId() != null ? item.getItemId() : "Unknown ID");
        	        itemMap.put("name", item.getItemName() != null ? item.getItemName() : "Unknown Name");
        	        return itemMap;
        	    })
        	    .collect(Collectors.toList());

        response.put("availableItems", availableItemsList);

        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(response);
    }

    // ✅ Handle Insufficient Stock Exception
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientStock(InsufficientStockException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(response);
    }

    // ✅ Handle Generic Exceptions (Catches all unexpected errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "An unexpected error occurred.");
        response.put("details", ex.getMessage()); // ✅ Debugging info

        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(response);
    }
}
