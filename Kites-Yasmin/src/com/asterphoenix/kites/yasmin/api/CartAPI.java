package com.asterphoenix.kites.yasmin.api;

import retrofit.Callback;
import retrofit.http.GET;

import com.asterphoenix.kites.model.Order;

public interface CartAPI {
	
	@GET("/api/json/cart/addorder")
	public void addOrder(Order order, Callback<String> response);

}
