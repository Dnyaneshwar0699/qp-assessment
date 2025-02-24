package com.qb.assessment.grocery.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Data
public class GroceryItemRequestDto {
	
	 @JsonProperty
	    private Long itemId;
	    
	    @JsonProperty
	    private String itemName;

	    // Getters
	    public Long getItemId() {
	        return itemId;
	    }

	    public String getItemName() {
	        return itemName;
	    }

	    // Setters
	    public void setItemId(Long itemId) {
	        this.itemId = itemId;
	    }

	    public void setItemName(String itemName) {
	        this.itemName = itemName;
	    }
	}

