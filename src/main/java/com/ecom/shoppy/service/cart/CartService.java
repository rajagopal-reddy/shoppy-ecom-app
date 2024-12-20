package com.ecom.shoppy.service.cart;

import com.ecom.shoppy.exception.ResourceNotFoundException;
import com.ecom.shoppy.model.Cart;
import com.ecom.shoppy.model.User;
import com.ecom.shoppy.repository.CartItemRepository;
import com.ecom.shoppy.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
	
	private final CartRepository cartRepository;
	private final CartItemRepository cartIteamRepository;
	private final AtomicLong cartIdGenerator = new AtomicLong(0);

	@Override
	public Cart getCart(Long id) {
		// TODO Auto-generated method stub
		Cart cart = cartRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Cart not found !"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		return cartRepository.save(cart);
	}

	@Transactional
	@Override
	public void clearCart(Long id) {
		Cart cart = getCart(id);
		cartIteamRepository.deleteAllByCartId(id);
		cart.getItems().clear();
		cartRepository.deleteById(id);
		
	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		// TODO Auto-generated method stub
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}

	@Override
	public Cart initializeNewCart(User user) {
		return Optional.ofNullable(getCartByUserId(user.getUserId()))
				.orElseGet(() -> {
					Cart cart = new Cart();
					cart.setUser(user);
					return cartRepository.save(cart);
				});
	}

	@Override
	public Cart getCartByUserId(Long userId) {
		return cartRepository.findByUserId(userId);
	}
}