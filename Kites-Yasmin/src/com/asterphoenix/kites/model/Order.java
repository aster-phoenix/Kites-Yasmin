package com.asterphoenix.kites.model;

import java.io.Serializable;
import java.util.Set;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private long orderID;
	private float totalPrice;
	private OrderStatus orderStatus;
	private Set<OrderItem> orders;

	public enum OrderStatus  {
		New, Completed, Verified, Rejected
	}
	
	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Set<OrderItem> getOrders() {
		return orders;
	}

	public void setOrders(Set<OrderItem> orders) {
		this.orders = orders;
	}
}

