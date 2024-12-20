package com.ecom.shoppy.controller;

import java.util.List;

import com.ecom.shoppy.dto.OrderDto;
import com.ecom.shoppy.exception.ResourceNotFoundException;
import com.ecom.shoppy.model.Order;
import com.ecom.shoppy.response.ApiResponse;
import com.ecom.shoppy.service.order.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
	
	private final IOrderService orderService;
	
	@PostMapping("/order")
	public ResponseEntity<ApiResponse> createOrder (@RequestParam Long userId){
		try {
			Order order = orderService.placeOrder(userId);
			OrderDto orderDto = orderService.convertToDto(order);
			return ResponseEntity.ok(new ApiResponse("Item order success !", orderDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error Occured !", e.getMessage() ));
		}
	}
	
	@GetMapping("/orderId/{orderId}/order")
	public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
		try {
			OrderDto order = orderService.getOrder(orderId);
			return ResponseEntity.ok(new ApiResponse(" Order found !", order));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse("Oops !", e.getMessage()));
		}				
	}
	
	@GetMapping("/userId/{userId}/order")
	public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
		try {
			List<OrderDto> order = orderService.getUserOrder(userId);
			return ResponseEntity.ok(new ApiResponse(" Order found !", order));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse("Oops !", e.getMessage()));
		}				
	}
	
	
	
	
	
	
	
	
	
	
	

}