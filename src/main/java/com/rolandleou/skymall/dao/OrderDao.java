package com.rolandleou.skymall.dao;

import java.util.List;

import com.rolandleou.skymall.model.OrderItem;

public interface OrderDao {

	Integer createOrder(Integer userId, Integer totalAmount);
	
	void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}