package com.rolandleou.skymall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rolandleou.skymall.dao.ProductDao;
import com.rolandleou.skymall.model.Product;
import com.rolandleou.skymall.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Override
	public Product getProductById(Integer productId) {
		return productDao.getProductById(productId);
	}

}
