package com.rolandleou.skymall.service;

import com.rolandleou.skymall.dto.CreateOrderRequest;

public interface OrderService {

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
