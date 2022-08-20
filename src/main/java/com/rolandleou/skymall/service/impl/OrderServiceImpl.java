package com.rolandleou.skymall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rolandleou.skymall.dao.OrderDao;
import com.rolandleou.skymall.dao.ProductDao;
import com.rolandleou.skymall.dto.BuyItem;
import com.rolandleou.skymall.dto.CreateOrderRequest;
import com.rolandleou.skymall.model.Order;
import com.rolandleou.skymall.model.OrderItem;
import com.rolandleou.skymall.model.Product;
import com.rolandleou.skymall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Override
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
		int totalAmount = 0;
		List<OrderItem> orderItemList = new ArrayList<>();
		
		for (BuyItem buyItem: createOrderRequest.getBuyItemList()) {
			Product product = productDao.getProductById(buyItem.getProductId());
			
			// calculate total price
			int amount = product.getPrice() * buyItem.getQuantity();
			totalAmount += amount;
			
			// Transfer BuItem to OrderItem
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(buyItem.getProductId());
			orderItem.setQuantity(buyItem.getQuantity());
			orderItem.setAmount(amount);
			
			orderItemList.add(orderItem);
		}
		
		Integer orderId = orderDao.createOrder(userId, totalAmount);
		
		orderDao.createOrderItems(orderId, orderItemList);
		
		return orderId;
	}

	@Override
	public Order getOrderById(Integer orderId) {
		
		Order order = orderDao.getOrderById(orderId);
		
		List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
		
		order.setOrderItemList(orderItemList);
		
		return order;
	}

}
