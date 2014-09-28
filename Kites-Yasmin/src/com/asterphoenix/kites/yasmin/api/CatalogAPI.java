package com.asterphoenix.kites.yasmin.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import com.asterphoenix.kites.model.Category;
import com.asterphoenix.kites.model.Product;

public interface CatalogAPI {
	
	@GET("/api/json/catalog")
	public void getCatalog(Callback<List<Category>> response);
	
	@GET("/api/json/catalog/products/byname/{productName}")
	public void getProductsByName(@Path("productName") String productName, Callback<List<Product>> response);
	
	@GET("/api/json/catalog/products/bycatalog/{catalogName}")
	public void getProductsByCategory(@Path("catalogName") String categoryName, Callback<List<Product>> response);

}
