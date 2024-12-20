package com.ecom.shoppy.repository;

import com.ecom.shoppy.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUserId(Long userId);

}