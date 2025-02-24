package com.qb.assessment.grocery.booking.controller;

import static com.qb.assessment.grocery.booking.constant.AppConstants.CROSS_ORIGIN_PERMIT_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qb.assessment.grocery.booking.constant.AppConstants;
import com.qb.assessment.grocery.booking.dto.GroceryDetailsResponseDto;
import com.qb.assessment.grocery.booking.dto.GroceryItemRequestDto;
import com.qb.assessment.grocery.booking.dto.OrderRequestDto;
import com.qb.assessment.grocery.booking.dto.OrderResponseDto;
import com.qb.assessment.grocery.booking.dto.ResponseBodyListDto;
import com.qb.assessment.grocery.booking.service.GroceryDataService;
import com.qb.assessment.grocery.booking.service.OrderService;

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
public class GroceryUserController {

    @Autowired
    GroceryDataService groceryDataService;
    @Autowired
    OrderService orderService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = AppConstants.SUCCESSFUL, description = "Get All Grocery Details", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
                     schema = @Schema(implementation = ResponseBodyListDto.class))),
        @ApiResponse(responseCode = AppConstants.UNAUTHORISED, description = AppConstants.UNAUTHORISED_DESC),
        @ApiResponse(responseCode = AppConstants.FORBIDDEN, description = AppConstants.FORBIDDEN_DESC)
    })
    @PostMapping(value = "/getgrocerydetails", consumes = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<List<GroceryDetailsResponseDto>> AllGroceryDetailsDependsOnIdAndName(
            @RequestBody GroceryItemRequestDto groceryItemRequestDto) throws NotFoundException {
        List<GroceryDetailsResponseDto> groceryDetailsResponseDto =  groceryDataService.fetchAllGroceryDetails(groceryItemRequestDto);
        return ResponseEntity.ok().body(groceryDetailsResponseDto);
    }
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = AppConstants.SUCCESSFUL, description = "Order placed successfully", 
                         content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
                         schema = @Schema(implementation = OrderResponseDto.class))),
            @ApiResponse(responseCode = AppConstants.UNAUTHORISED, description = AppConstants.UNAUTHORISED_DESC),
            @ApiResponse(responseCode = AppConstants.FORBIDDEN, description = AppConstants.FORBIDDEN_DESC)
        })
        @PostMapping(value = "/bookgrocery", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<OrderResponseDto> bookGroceryOrder(@RequestBody OrderRequestDto orderRequestDto) {
            
            System.out.println("Placing order for user: {}" + orderRequestDto.getUserId());
            OrderResponseDto orderResponse = orderService.placeOrder(orderRequestDto);
            
            return ResponseEntity.ok().body(orderResponse);
        }
}
