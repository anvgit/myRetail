package com.anvesh.target.myretailrestfulapi;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.spring.commons.config.AsyncConfig.withTimeout;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.anvesh.target.model.ProductInfo;
import com.anvesh.target.model.ProductPrice;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MyRetailRestfulApiApplicationTest {

	@MockBean
	RestTemplate mockRestTemplate;

	@Autowired
	private WebApplicationContext cont;

	ProductInfo productInfo;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		RestAssuredMockMvc.webAppContextSetup(cont);
		RestAssuredMockMvc.config().asyncConfig(withTimeout(500, TimeUnit.MILLISECONDS));

		ProductPrice productCost = new ProductPrice(34.89, "USD");
		productInfo = new ProductInfo(13860428, "The Big Lebowski (Blu-ray)", productCost);
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

}
