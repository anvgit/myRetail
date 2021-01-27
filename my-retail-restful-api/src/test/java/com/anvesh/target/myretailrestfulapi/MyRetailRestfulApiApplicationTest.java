package com.anvesh.target.myretailrestfulapi;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.spring.commons.config.AsyncConfig.withTimeout;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.anvesh.target.dao.ProductInfoDao;
import com.anvesh.target.entity.ProductInfoEntity;
import com.anvesh.target.model.ProductInfo;
import com.anvesh.target.model.ProductPrice;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MyRetailRestfulApiApplicationTest {

	@MockBean
	RestTemplate mockRestTemplate;

	@Autowired
	private WebApplicationContext cont;

	ProductInfo productInfo;

	ProductInfo productInfoUpdate;

	@Mock
	ProductInfoDao mockProductInfoDao;

	private Long id;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		RestAssuredMockMvc.webAppContextSetup(cont);
		RestAssuredMockMvc.config().asyncConfig(withTimeout(500, TimeUnit.MILLISECONDS));

		ProductPrice productCost = new ProductPrice(13.49, "USD");
		id = (long) 13860428;
		productInfo = new ProductInfo(id, "The Big Lebowski (Blu-ray)", productCost);
	}

	@Test
	public void getProductInfoTest() {
		String redSkyOutput = "{\"product\":{\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\"}}}}";
		ResponseEntity<String> redSkyResponse = new ResponseEntity<String>(redSkyOutput, HttpStatus.OK);

		Mockito.when(mockRestTemplate.getForEntity(Mockito.anyString(), Mockito.eq(String.class)))
				.thenReturn(redSkyResponse);

		ProductInfo response = given().when().get("/products/13860428").then().statusCode(200).extract()
				.as(ProductInfo.class);

		Assert.assertEquals(13860428, response.getId());
		Assert.assertEquals("The Big Lebowski (Blu-ray)", response.getName());
		Assert.assertTrue(response.getCurrent_price().getValue() == 13.49);
	}

	@Test
	public void updateProductInfoTest() {

		ProductPrice productCostUpdate = new ProductPrice(55.67, "INR");
		productInfoUpdate = new ProductInfo(id, "The Big Lebowski (Blu-ray)", productCostUpdate);

		ProductInfoEntity entity = new ProductInfoEntity();
		entity.setCurrency_code("USD");
		entity.setValue(13.49);
		entity.setId(13860428);

		Mockito.when(mockProductInfoDao.findById((long) 13860428)).thenReturn(Optional.of(entity));
		
		Mockito.when(mockProductInfoDao.save(Mockito.any(ProductInfoEntity.class))).thenReturn(entity);
		
		given().contentType(ContentType.JSON).body(productInfoUpdate).when().put("/products/13860428").then().statusCode(200);
	}
}
