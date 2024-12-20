package com.ecom.shoppy.controller;

import java.math.BigDecimal;

import com.ecom.shoppy.exception.ResourceNotFoundException;
import com.ecom.shoppy.model.Cart;
import com.ecom.shoppy.response.ApiResponse;
import com.ecom.shoppy.service.cart.ICartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {

	private final ICartService cartService;
	
	@GetMapping("/{cartId}/my-cart")
	public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
		try {
			Cart cart = cartService.getCart(cartId);
			return ResponseEntity.ok(new ApiResponse("Success !", cart));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		} 
	}
	
	@DeleteMapping("/{cartId}/clear")
	public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
		try {
			cartService.clearCart(cartId);
			return ResponseEntity.ok(new ApiResponse("Clear cart success !", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@GetMapping("/{cartId}/cart/total-price")
	public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId){
		try {
			BigDecimal totalPrice = cartService.getTotalPrice(cartId);
			return ResponseEntity.ok(new ApiResponse("Total Price !", totalPrice));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
	}
}