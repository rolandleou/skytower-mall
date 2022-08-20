package com.rolandleou.skymall.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.rolandleou.skymall.dao.OrderDao;
import com.rolandleou.skymall.dto.OrderQueryParams;
import com.rolandleou.skymall.model.Order;
import com.rolandleou.skymall.model.OrderItem;
import com.rolandleou.skymall.rowmapper.OrderItemRowMapper;
import com.rolandleou.skymall.rowmapper.OrderRowMapper;

@Component
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public Integer createOrder(Integer userId, Integer totalAmount) {
		String sql = "INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) "
				+ "VALUES(:userId, :totalAmount, :createdDate, :lastModifiedDate)";
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("totalAmount", totalAmount);
		
		Date now = new Date();
		map.put("createdDate", now);
		map.put("lastModifiedDate", now);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		
		Integer orderId = keyHolder.getKey().intValue();
		
		return orderId;
	}

	@Override
	public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
		// Use batchUpdate to add all data at same time, more efficiency
		String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) "
				+ "VALUES(:orderId, :productId, :quantity, :amount)";
		
		MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];		
		for (int i = 0; i < orderItemList.size(); i++) {
			OrderItem orderItem = orderItemList.get(i);
			
			parameterSources[i] = new MapSqlParameterSource();
			parameterSources[i].addValue("orderId", orderId);
			parameterSources[i].addValue("productId", orderItem.getProductId());
			parameterSources[i].addValue("quantity", orderItem.getQuantity());
			parameterSources[i].addValue("amount", orderItem.getAmount());
			
		}
		
		namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
		
	}

	@Override
	public Order getOrderById(Integer orderId) {
		String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date "
				+ "FROM `order` WHERE order_id = :orderId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		
		List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
		
		if (orderList.size() > 0) {
			return orderList.get(0);
		} else {
			return null;			
		}
	}

	@Override
	public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
		String sql = "SELECT order_item_id, order_id, product_id, quantity, amount "
				+ "FROM order_item WHERE order_id = :orderId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		
		List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
		
		return orderItemList;
	}

	@Override
	public List<Order> getOrders(OrderQueryParams orderQueryParams) {
		String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date "
				+ "FROM `order` WHERE 1 = 1";
	
		Map<String, Object> map = new HashMap<>();
		// Query condition
		sql = addFilteringSql(sql, map, orderQueryParams);
		
		// Sorting
		sql = sql + " ORDER BY created_date DESC";
		
		// Pagination
		sql = sql + " LIMIT :limit OFFSET :offset";
		map.put("limit", orderQueryParams.getLimit());
		map.put("offset", orderQueryParams.getOffset());
		
		List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
		
		return orderList;
	}

	@Override
	public Integer countOrder(OrderQueryParams orderQueryParams) {
		String sql = "SELECT count(*) FROM `order` WHERE 1 = 1";

		Map<String, Object> map = new HashMap<>();
		// Query condition
		sql = addFilteringSql(sql, map, orderQueryParams);
		
		Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
		
		return total;
	}
	
	private String addFilteringSql(String sql, Map<String, Object> map, OrderQueryParams orderQueryParams) {
		if (orderQueryParams.getUserId() != null) {
			sql = sql + " AND user_id = :userId";
			map.put("userId", orderQueryParams.getUserId());
		}
		
		return sql;
	}

}
