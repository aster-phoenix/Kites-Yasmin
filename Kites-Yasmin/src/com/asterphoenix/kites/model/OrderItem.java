package com.asterphoenix.kites.model;

import java.io.Serializable;

public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private long singleOrderID;
	private long productID;
	private String productName;
	private float productPrice;
	private String imageBytes;
	private float qty;
	
	public long getSingleOrderID() {
		return singleOrderID;
	}
	public void setSingleOrderID(long singleOrderID) {
		this.singleOrderID = singleOrderID;
	}
	public long getProductID() {
		return productID;
	}
	public void setProductID(long productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public String getImageBytes() {
		return imageBytes;
	}
	public void setImageBytes(String imageBytes) {
		this.imageBytes = imageBytes;
	}
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	
}
