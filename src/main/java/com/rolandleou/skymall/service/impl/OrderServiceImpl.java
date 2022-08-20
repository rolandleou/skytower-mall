package com.rolandleou.skymall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.rolandleou.skymall.dao.OrderDao;
import com.rolandleou.skymall.dao.ProductDao;
import com.rolandleou.skymall.dao.UserDao;
import com.rolandleou.skymall.dto.BuyItem;
import com.rolandleou.skymall.dto.CreateOrderRequest;
import com.rolandleou.skymall.model.Order;
import com.rolandleou.skymall.model.OrderItem;
import com.rolandleou.skymall.model.Product;
import com.rolandleou.skymall.model.User;
import com.rolandleou.skymall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {

	private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
		// Check if user exist
		User user = userDao.getUserById(userId);
		
		if (user == null) {
			log.warn("Found UserId {} not exist!!", userId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		int totalAmount = 0;
		List<OrderItem> orderItemList = new ArrayList<>();
		
		for (BuyItem buyItem: createOrderRequest.getBuyItemList()) {
			Product product = productDao.getProductById(buyItem.getProductId());
			
			// Check if product exist and if stock in warehouse is enough
			if (product == null) {
				log.warn("Product {} not exist!!", buyItem.getProductId());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);				
			} else if (buyItem.getQuantity() > product.getStock()) {
				log.warn("product {} stock is not enough, left stock {}, want to buy quantity {}", 
						buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			
			// deduct stock from database
			productDao.updateStock(buyItem.getProductId(), product.getStock()-buyItem.getQuantity());
			
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
