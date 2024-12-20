package com.ecom.shoppy.repository;

import com.ecom.shoppy.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	void deleteAllByCartId(Long id);

}