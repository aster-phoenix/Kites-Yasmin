package com.asterphoenix.kites.model;


public class Customer {
	
	private long cutomerID;
	private String customerFName;
	private String customerLName;
	private String customerUsername;
	private String customerPassword;
	private String customerEmail;
	private boolean customerIsActive;
	
	public long getCutomerID() {
		return cutomerID;
	}
	public void setCutomerID(long cutomerID) {
		this.cutomerID = cutomerID;
	}
	public String getCustomerFName() {
		return customerFName;
	}
	public void setCustomerFName(String customerFName) {
		this.customerFName = customerFName;
	}
	public String getCustomerLName() {
		return customerLName;
	}
	public void setCustomerLName(String customerLName) {
		this.customerLName = customerLName;
	}
	public String getCustomerUsername() {
		return customerUsername;
	}
	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}
	public String getCustomerPassword() {
		return customerPassword;
	}
	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public boolean isCustomerIsActive() {
		return customerIsActive;
	}
	public void setCustomerIsActive(boolean customerIsActive) {
		this.customerIsActive = customerIsActive;
	}

}
