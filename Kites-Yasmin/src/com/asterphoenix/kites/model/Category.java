package com.asterphoenix.kites.model;

import java.util.Set;

public class Category {
	
	private long categoryID;
	private String categoryName;
	private String categoryDescription;
	private Set<Product> products;
	private String imageBytes;
	
	public long getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	public String getImageBytes() {
		return imageBytes;
	}
	public void setImageBytes(String imageBytes) {
		this.imageBytes = imageBytes;
	}

}
