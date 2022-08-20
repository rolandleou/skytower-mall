package com.rolandleou.skymall.service;

import com.rolandleou.skymall.dto.CreateOrderRequest;
import com.rolandleou.skymall.model.Order;

public interface OrderService {

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
	
	Order getOrderById(Integer orderId);
	
}
