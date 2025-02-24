package com.qb.assessment.grocery.booking.service;

import com.qb.assessment.grocery.booking.dto.OrderRequestDto;
import com.qb.assessment.grocery.booking.dto.OrderResponseDto;

public interface OrderService {

	public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto);

}
