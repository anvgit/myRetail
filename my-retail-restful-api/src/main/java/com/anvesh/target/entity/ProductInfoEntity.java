package com.anvesh.target.entity;

import org.springframework.data.annotation.Id;

public class ProductInfoEntity {
	@Id
	private long id;
	private double value;
	private String currency_code;

	public ProductInfoEntity() {

	}

	public ProductInfoEntity(long id, double value, String currency_code) {
		super();
		this.id = id;
		this.value = value;
		this.currency_code = currency_code;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	@Override
	public String toString() {
		return "ProductInfoEntity [id=" + id + ", value=" + value + ", currency_code=" + currency_code + "]";
	}

}
