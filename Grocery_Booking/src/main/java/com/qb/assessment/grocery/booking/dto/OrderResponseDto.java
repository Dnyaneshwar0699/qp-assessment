package com.qb.assessment.grocery.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDto {
    
    @JsonProperty
    private Long orderId;

    @JsonProperty
    private String message;

    // Default constructor
//    public OrderResponseDto() {}

    // Parameterized constructor
    public OrderResponseDto(Long orderId, String message) {
        this.orderId = orderId;
        this.message = message;
    }

    // Getter for orderId
    public Long getOrderId() {
        return orderId;
    }

    // Setter for orderId
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Setter for message
    public void setMessage(String message) {
        this.message = message;
    }
}
