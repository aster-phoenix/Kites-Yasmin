package com.asterphoenix.kites.model;

import java.io.Serializable;

public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private long singleOrderID;
	private long productID;
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
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	
}
