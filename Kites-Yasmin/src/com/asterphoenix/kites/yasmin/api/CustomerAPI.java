package com.asterphoenix.kites.yasmin.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.asterphoenix.kites.model.Customer;

public interface CustomerAPI {
	
	@POST("/api/json/customer/addcustomer")
	public void addCustomer(@Body Customer customer, Callback<String> response);
	
	@POST("/api/json/customer/logcustomerin")
	public void logCustomerIn(@Body Customer customer, Callback<Customer> response);
	
	@GET("/api/json/customer/byid/{id}")
	public void getCustomerByID(@Path("id") long id, Callback<Customer> respone);
}
