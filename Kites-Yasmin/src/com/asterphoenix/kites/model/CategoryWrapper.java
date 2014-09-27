package com.asterphoenix.kites.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryWrapper extends ArrayList<Category> {
	
	public CategoryWrapper() {
		super();
	}
	
	public CategoryWrapper(Collection<? extends Category> c) {
		super(c);
	}
	
	public List<Category> getCategoy() {
		return this;
	}

	public void setCategoy(List<Category> c) {
		this.addAll(c);
	}

}
