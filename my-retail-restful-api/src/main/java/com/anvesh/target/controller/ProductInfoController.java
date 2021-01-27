package com.anvesh.target.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anvesh.target.model.ProductInfo;
import com.anvesh.target.service.ProductInfoService;

@RestController
@RequestMapping("/products")
public class ProductInfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoController.class);

	@Autowired
	ProductInfoService productInfoService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductInfo> getProductInfo(@Valid @PathVariable long id) {

		LOGGER.debug("in controller get() logger id==== {}", id);

		ResponseEntity<ProductInfo> response = productInfoService.getProductInfo(id);
		LOGGER.debug("response==== {}", response);
		return response;
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductInfo> updateProductInfo(@Valid @PathVariable long id,
			@RequestBody ProductInfo productInfo) {

		LOGGER.debug("in controller update() logger id==== {}", id);
		LOGGER.debug("in controller update() logger productInfo==== {}", productInfo);

		ResponseEntity<ProductInfo> response = productInfoService.updateProductInfo(id, productInfo);
		LOGGER.debug("response==== {}", response);
		return response;
	}
}
