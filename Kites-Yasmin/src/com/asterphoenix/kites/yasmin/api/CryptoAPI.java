package com.asterphoenix.kites.yasmin.api;

import com.asterphoenix.kites.yasmin.helper.HandShakeData;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface CryptoAPI {
	
	@POST("/api/json/crypto/handshake")
	public void handShake(@Body HandShakeData data, Callback<HandShakeData> response);
	
	@GET("/api/json/crypto/getpublic")
	public void getPublic(Callback<String> response);
	
	@GET("/api/json/crypto/getiv")
	public void getIv(Callback<String> response);
	
	@POST("/api/json/crypto/getsecret")
	public void getSecret(@Body String pk, Callback<String> response);
	
}