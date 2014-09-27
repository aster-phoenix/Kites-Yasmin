package com.asterphoenix.kites.yasmin;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

import com.asterphoenix.kites.model.Category;

public interface CatalogAPI {
	
	@GET("/api/json/catalog")
	public void getCatalog(Callback<List<Category>> response);

}
