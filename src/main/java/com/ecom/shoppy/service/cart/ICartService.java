package com.ecom.shoppy.service.cart;

import com.ecom.shoppy.model.Cart;
import com.ecom.shoppy.model.User;

import java.math.BigDecimal;

public interface ICartService {
	
	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
	Cart getCartByUserId(Long userId);
	Cart initializeNewCart(User user);

}