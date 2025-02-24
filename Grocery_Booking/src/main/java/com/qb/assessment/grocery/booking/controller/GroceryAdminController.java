package com.qb.assessment.grocery.booking.controller;

import static com.qb.assessment.grocery.booking.constant.AppConstants.CROSS_ORIGIN_PERMIT_VALUE;

import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qb.assessment.grocery.booking.constant.AppConstants;
import com.qb.assessment.grocery.booking.dto.GroceryAdminItemRequestDto;
import com.qb.assessment.grocery.booking.dto.GroceryDetailsResponseDto;
import com.qb.assessment.grocery.booking.dto.ResponseBodyListDto;
import com.qb.assessment.grocery.booking.service.GroceryAdminService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("${request.mapping.url.prefix}")
@CrossOrigin(CROSS_ORIGIN_PERMIT_VALUE)
//@SecurityRequirement(name = SECURITY_AUTH)
public class GroceryAdminController {

    @Autowired
    private GroceryAdminService groceryAdminService;
   // @Autowired
   // GroceryAdminItemRequestDto groceryAdminItemRequestDto;

    @ApiResponses(value = {
        @ApiResponse(responseCode = AppConstants.SUCCESSFUL, description = "Grocery Item Added Successfully", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
                     schema = @Schema(implementation = GroceryDetailsResponseDto.class))),
        @ApiResponse(responseCode = AppConstants.UNAUTHORISED, description = AppConstants.UNAUTHORISED_DESC),
        @ApiResponse(responseCode = AppConstants.FORBIDDEN, description = AppConstants.FORBIDDEN_DESC)
    })
    @PostMapping(value = "/addgrocery", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroceryDetailsResponseDto> addGroceryItem(@RequestBody GroceryAdminItemRequestDto groceryAdminItemRequestDto) {
    	System.out.println("Adding new grocery item: {}" + groceryAdminItemRequestDto.getItemName());
        GroceryDetailsResponseDto newItem = groceryAdminService.addGroceryItem(groceryAdminItemRequestDto);
        return ResponseEntity.ok().body(newItem);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = AppConstants.SUCCESSFUL, description = "Fetched All Grocery Items", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
                     schema = @Schema(implementation = ResponseBodyListDto.class))),
        @ApiResponse(responseCode = AppConstants.UNAUTHORISED, description = AppConstants.UNAUTHORISED_DESC),
        @ApiResponse(responseCode = AppConstants.FORBIDDEN, description = AppConstants.FORBIDDEN_DESC)
    })
    @GetMapping(value = "/getallgrocery", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GroceryDetailsResponseDto>> getAllGroceryItems() {
    	System.out.println("Fetching all grocery items");
        List<GroceryDetailsResponseDto> items = groceryAdminService.getAllGroceryItems();
        return ResponseEntity.ok().body(items);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = AppConstants.SUCCESSFUL, description = "Grocery Item Deleted Successfully"),
        @ApiResponse(responseCode = AppConstants.UNAUTHORISED, description = AppConstants.UNAUTHORISED_DESC),
        @ApiResponse(responseCode = AppConstants.FORBIDDEN, description = AppConstants.FORBIDDEN_DESC)
    })
//    @DeleteMapping(value = "/deletegrocery/{id}")
//    public ResponseEntity<String> deleteGroceryItem(@PathVariable Long id) {
//    	System.out.println("Deleting grocery item with ID: {}" + id);
//        groceryAdminService.deleteGroceryItem(id);
//        return ResponseEntity.ok("Grocery item with ID " + id + " has been deleted.");
//    }
    @DeleteMapping(value = "/deletegrocery")
    public ResponseEntity<String> deleteGroceryItem(@RequestBody Map<String, Long> request) {
        Long itemId = request.get("itemId");
        if (itemId == null) {
            return ResponseEntity.badRequest().body("Item ID is required");
        }
        
        boolean isDeleted = groceryAdminService.deleteGroceryItem(itemId);
        
        if (isDeleted) {
            return ResponseEntity.ok("Item deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Item not found");
        }
    }


    @ApiResponses(value = {
        @ApiResponse(responseCode = AppConstants.SUCCESSFUL, description = "Grocery Item Updated Successfully", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
                     schema = @Schema(implementation = GroceryDetailsResponseDto.class))),
        @ApiResponse(responseCode = AppConstants.UNAUTHORISED, description = AppConstants.UNAUTHORISED_DESC),
        @ApiResponse(responseCode = AppConstants.FORBIDDEN, description = AppConstants.FORBIDDEN_DESC)
    })
    @PutMapping(value = "/updategrocery/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroceryDetailsResponseDto> updateGroceryItem(@PathVariable Long id, 
                                                                     @RequestBody GroceryAdminItemRequestDto groceryAdminItemRequestDto) {
    	System.out.println("Updating grocery item with ID: {}" + id);
        GroceryDetailsResponseDto updatedItem = groceryAdminService.updateGroceryItem(id, groceryAdminItemRequestDto);
        return ResponseEntity.ok().body(updatedItem);
    }

    @ApiResponses(value = {
    	    @ApiResponse(responseCode = AppConstants.SUCCESSFUL, description = "Inventory Updated Successfully"),
    	    @ApiResponse(responseCode = AppConstants.UNAUTHORISED, description = AppConstants.UNAUTHORISED_DESC),
    	    @ApiResponse(responseCode = AppConstants.FORBIDDEN, description = AppConstants.FORBIDDEN_DESC)
    	})
    @PutMapping("/updateinventory/{id}")
    public ResponseEntity<GroceryDetailsResponseDto> updateInventory(
            @PathVariable Long id, 
            @RequestBody Map<String, Integer> request) {
        
        if (!request.containsKey("quantity")) {
            return ResponseEntity.badRequest().body(null);
        }

        int newStockQuantity = request.get("quantity");
        GroceryDetailsResponseDto updatedItem = groceryAdminService.updateInventory(id, newStockQuantity);

        return ResponseEntity.ok(updatedItem);
    
}
}
