package com.rolandleou.skymall.service;

import com.rolandleou.skymall.dto.ProductRequest;
import com.rolandleou.skymall.model.Product;

public interface ProductService {

	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
}
