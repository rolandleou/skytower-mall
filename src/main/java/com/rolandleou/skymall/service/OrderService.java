package com.rolandleou.skymall.service;

import java.util.List;

import com.rolandleou.skymall.dto.CreateOrderRequest;
import com.rolandleou.skymall.dto.OrderQueryParams;
import com.rolandleou.skymall.model.Order;

public interface OrderService {

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
	
	Order getOrderById(Integer orderId);
	
	List<Order> getOrders(OrderQueryParams orderQueryParams);
	
	Integer countOrder(OrderQueryParams orderQueryParams);
	
}
