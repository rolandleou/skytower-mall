package com.rolandleou.skymall.dao;

import java.util.List;

import com.rolandleou.skymall.constant.ProductCategory;
import com.rolandleou.skymall.dto.ProductRequest;
import com.rolandleou.skymall.model.Product;

public interface ProductDao {

	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void deleteProductById(Integer productId);
	
	List<Product> getProducts(ProductCategory category, String search);
}
