package com.anvesh.target.service;

import java.util.Optional;

import org.bson.json.JsonParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.anvesh.target.dao.ProductInfoDao;
import com.anvesh.target.entity.ProductInfoEntity;
import com.anvesh.target.model.ProductInfo;
import com.anvesh.target.model.ProductPrice;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	@Value("${redSky.endpoint}")
	private String REDSKY_ENDPOINT;
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ProductInfoDao productInfoDao;

	@Override
	public ResponseEntity<ProductInfo> getProductInfo(long id) {

		ProductInfo productInfo = new ProductInfo();
		ProductPrice productPrice = new ProductPrice();
		LOGGER.debug("ProductInfoServiceImpl get Id=== {}", id);
		String productName;

		ResponseEntity<String> redSkyObject = null;

		try {
			redSkyObject = restTemplate.getForEntity(REDSKY_ENDPOINT, String.class);
		} catch (RestClientException e) {
			throw new RestClientException("Exception while calling the GET Endpoint for target red sky api");
		}
		LOGGER.debug("ProductInfoServiceImpl get() redSkyObject=== {}", redSkyObject);

		String resp = redSkyObject.getBody();
		LOGGER.debug("ProductInfoServiceImpl get() resp=== {}", resp);

		try {
			JSONObject redSkyJson = new JSONObject(resp);
			JSONObject redSkyProduct = redSkyJson.getJSONObject("product");
			JSONObject redSkyItem = redSkyProduct.getJSONObject("item");
			JSONObject redSkyProdDes = redSkyItem.getJSONObject("product_description");
			productName = redSkyProdDes.getString("title");
		} catch (JSONException je) {
			throw new JsonParseException("Exception occured while parsing response from redsky api " + je);
		}

		Optional<ProductInfoEntity> dbResponse;
		try {
			dbResponse = productInfoDao.findById(id);
		} catch (Exception dae) {
			throw new DataAccessResourceFailureException("Exception occured while retrieving id", dae);
		}
		if (dbResponse.isPresent()) {
			productPrice.setCurrency_code(dbResponse.get().getCurrency_code());

			productPrice.setValue(dbResponse.get().getValue());

			productInfo.setCurrent_price(productPrice);
			productInfo.setId(dbResponse.get().getId());
		} else {
			return new ResponseEntity<ProductInfo>(HttpStatus.NOT_FOUND);
		}

		LOGGER.debug("productName debug=== {}", productName);

		productInfo.setName(productName);
		LOGGER.debug("ProductInfo debug=== {}", productInfo);

		return new ResponseEntity<ProductInfo>(productInfo, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ProductInfo> updateProductInfo(long id, ProductInfo productInfo) {

		Optional<ProductInfoEntity> isProductExists;
		try {
			isProductExists = productInfoDao.findById(id);
		} catch (Exception dae) {
			throw new DataAccessResourceFailureException("Exception occured while retrieving id for update", dae);
		}
		boolean validId = false;
		ProductInfoEntity entity = null;

		if (isProductExists.isPresent()) {
			entity = isProductExists.get();
			entity.setCurrency_code(productInfo.getCurrent_price().getCurrency_code());
			entity.setValue(productInfo.getCurrent_price().getValue());
			if (entity.getId() == id) {
				validId = true;
			}
		} else {
			return new ResponseEntity<ProductInfo>(HttpStatus.BAD_REQUEST);
		}

		if (validId) {
			try {
				productInfoDao.save(entity);
				return new ResponseEntity<ProductInfo>(HttpStatus.OK);
			} catch (Exception e) {
				throw new DataAccessResourceFailureException("Exception occured while updating the NO-SQL by Id", e);
			}
		} else {
			return new ResponseEntity<ProductInfo>(HttpStatus.NOT_FOUND);
		}
	}
}
