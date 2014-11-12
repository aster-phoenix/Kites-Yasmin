package com.asterphoenix.kites.model;

import java.util.List;

public class CategoryWrapper {
	
	List<Category> category;
	
	public CategoryWrapper() {
//		super();
	}
	
	public CategoryWrapper(List<Category> c) {
//		super(c);
		category = c;
	}
	
	public List<Category> getCategoy() {
//		return this;
		return category;
	}

	public void setCategoy(List<Category> c) {
//		this.addAll(c);
		category.addAll(c);
	}

}
