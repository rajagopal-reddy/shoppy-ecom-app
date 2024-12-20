package com.ecom.shoppy.service.order;

import com.ecom.shoppy.dto.OrderDto;
import com.ecom.shoppy.model.Order;

import java.util.List;

public interface IOrderService {

	Order placeOrder(Long userId);
	OrderDto getOrder(Long orderId);
	List<OrderDto> getUserOrder(Long userId);
	OrderDto convertToDto(Order order);
}