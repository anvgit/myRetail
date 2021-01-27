package com.anvesh.target.model;

public class ProductInfo {
	private long id;
	private String name;
	private ProductPrice current_price;

	public ProductInfo() {

	}

	public ProductInfo(long id, String name, ProductPrice current_price) {
		super();
		this.id = id;
		this.name = name;
		this.current_price = current_price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductPrice getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(ProductPrice current_price) {
		this.current_price = current_price;
	}

	@Override
	public String toString() {
		return "ProductInfo [id=" + id + ", name=" + name + ", current_price=" + current_price + "]";
	}
}
