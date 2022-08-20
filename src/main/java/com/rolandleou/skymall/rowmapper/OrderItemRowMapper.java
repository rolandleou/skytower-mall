package com.rolandleou.skymall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rolandleou.skymall.model.OrderItem;

public class OrderItemRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderItem orderItem = new OrderItem();
		
		orderItem.setOrderItemId(rs.getInt("order_item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setProductId(rs.getInt("product_id"));
		orderItem.setQuantity(rs.getInt("quantity"));
		orderItem.setAmount(rs.getInt("amount"));
		
		return orderItem;
	}

}
