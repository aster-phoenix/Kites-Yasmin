package com.asterphoenix.kites.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductWrapper extends ArrayList<Product> {
	
	public ProductWrapper() {
		super();
	}
	
	public ProductWrapper(Collection<? extends Product> p) {
		super(p);
	}
	
	public List<Product> getProduct() {
		return this;
	}
	
	public void setProduct(List<Product> p) {
		this.addAll(p);
	}

}
