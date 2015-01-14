package com.asterphoenix.kites.yasmin.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.asterphoenix.kites.yasmin.helper.EncryptedOrderData;

public interface CartAPI {
	
	@POST("/api/json/cart/addorder")
	public void addOrder(@Body EncryptedOrderData eOrder, Callback<String> response);
	
	@GET("/api/json/cart/checkorder/{id}")
	public void checkOrder(@Path("id") long id, Callback<String> response);

}
