package com.asterphoenix.kites.model;


public class OrderItem {

	private long singleOrderID;
	private Product product;
	private int qty;
	private int price;
	private Order order;
	
	public long getSingleOrderID() {
		return singleOrderID;
	}
	public void setSingleOrderID(long singleOrderID) {
		this.singleOrderID = singleOrderID;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
