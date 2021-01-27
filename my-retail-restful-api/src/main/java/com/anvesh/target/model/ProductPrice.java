package com.anvesh.target.model;

public class ProductPrice {

	private double value;
	private String currency_code;

	public ProductPrice() {

	}

	public ProductPrice(double value, String currency_code) {
		super();
		this.value = value;
		this.currency_code = currency_code;
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
}
