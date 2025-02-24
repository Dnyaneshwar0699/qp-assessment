package com.qb.assessment.grocery.booking.entity;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Audited
@Entity(name = "GroceryItemDetails")
@Table(name = "grocery_item_details")
public class GroceryItemDetails {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long itemId;
	    
	    @Column(name = "item_name", nullable = false)
	    private String itemName;
	    
	    private double price;
	    private int itemQuantity;

	    // Getters and Setters
	    public Long getItemId() {
	        return itemId;
	    }

	    public void setItemId(Long itemId) {
	        this.itemId = itemId;
	    }

	    public String getItemName() {
	        return itemName;
	    }

	    public void setItemName(String itemName) {
	        this.itemName = itemName;
	    }

	    public double getPrice() {
	        return price;
	    }

	    public void setPrice(double price) {
	        this.price = price;
	    }

	    public int getItemQuantity() {
	        return itemQuantity;
	    }

	    public void setItemQuantity(int itemQuantity) {
	        this.itemQuantity = itemQuantity;
	    }
	}



