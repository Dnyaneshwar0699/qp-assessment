package com.qb.assessment.grocery.booking.exception;

public class ItemNotFoundException extends RuntimeException {
    private final Long groceryItemId; // ✅ Store the missing item ID

    public ItemNotFoundException(Long groceryItemId, String message) {
        super(message);
        this.groceryItemId = groceryItemId;
    }

    public Long getGroceryItemId() {
        return groceryItemId;
    }
}