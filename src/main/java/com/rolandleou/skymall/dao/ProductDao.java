package com.rolandleou.skymall.dao;

import com.rolandleou.skymall.dto.ProductRequest;
import com.rolandleou.skymall.model.Product;

public interface ProductDao {

	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
}
