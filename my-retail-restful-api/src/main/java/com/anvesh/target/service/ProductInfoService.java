package com.anvesh.target.service;

import org.springframework.http.ResponseEntity;

import com.anvesh.target.model.ProductInfo;

public interface ProductInfoService {
	public ResponseEntity<ProductInfo> getProductInfo(long id);

	public ResponseEntity<ProductInfo> updateProductInfo(long id, ProductInfo productInfo);
}
