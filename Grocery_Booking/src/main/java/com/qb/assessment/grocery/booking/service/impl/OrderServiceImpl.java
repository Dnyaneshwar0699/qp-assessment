package com.qb.assessment.grocery.booking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qb.assessment.grocery.booking.dto.OrderItemRequestDto;
import com.qb.assessment.grocery.booking.dto.OrderRequestDto;
import com.qb.assessment.grocery.booking.dto.OrderResponseDto;
import com.qb.assessment.grocery.booking.entity.GroceryItemDetails;
import com.qb.assessment.grocery.booking.entity.Order;
import com.qb.assessment.grocery.booking.entity.OrderItem;
import com.qb.assessment.grocery.booking.exception.InsufficientStockException;
import com.qb.assessment.grocery.booking.exception.ItemNotFoundException;
import com.qb.assessment.grocery.booking.repository.GroceryItemRepository;
import com.qb.assessment.grocery.booking.repository.OrderRepository;
import com.qb.assessment.grocery.booking.service.OrderService;


	@Service
	public class OrderServiceImpl implements OrderService {

	  
		@Autowired
	    private  GroceryItemRepository groceryItemRepository;
		@Autowired
		private OrderRepository orderRepository;
		
		@Override
		public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
		    Order order = new Order();
		    order.setUserId(orderRequestDto.getUserId());

		    List<OrderItem> processedOrderItems = new ArrayList<>();
		    List<String> errorMessages = new ArrayList<>();

		    for (OrderItemRequestDto itemRequest : orderRequestDto.getItems()) {
		    	GroceryItemDetails groceryItem = groceryItemRepository.findGroceryItemById(itemRequest.getGroceryItemId())
		    		    .orElseThrow(() -> new ItemNotFoundException(itemRequest.getGroceryItemId(), "Item not found with ID: " + itemRequest.getGroceryItemId()));

//		        if (optionalItem.isEmpty()) {
//		            throw new ItemNotFoundException("Item not found with ID: " + itemRequest.getGroceryItemId());
//		        }

		  //      GroceryItemDetails groceryItem = optionalItem.get();

		        int updatedRows = groceryItemRepository.updateStockQuantity(itemRequest.getGroceryItemId(), itemRequest.getQuantity());
		        if (updatedRows == 0) {
		            throw new InsufficientStockException("Insufficient stock for item: " + groceryItem.getItemName());
		        }

		        // Process the valid item
		        OrderItem orderItem = new OrderItem();
		        orderItem.setOrder(order);
		        orderItem.setGroceryItem(groceryItem);
		        orderItem.setQuantity(itemRequest.getQuantity());
		        processedOrderItems.add(orderItem);
		    }

		    if (processedOrderItems.isEmpty()) {
		        throw new RuntimeException("No valid items to process.");
		    }

		    order.setOrderItems(processedOrderItems);
		    orderRepository.save(order);

		    return new OrderResponseDto(order.getOrderId(), "Order placed successfully");
		}
	}