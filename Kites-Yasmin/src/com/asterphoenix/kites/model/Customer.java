package com.asterphoenix.kites.model;


public class Customer {
	
	private long customerID;
	private String customerFName;
	private String customerLName;
	private String customerUsername;
	private String customerPassword;
	private String customerEmail;
	private String customerAddress;
	private boolean customerIsActive;
	
	public long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(long cutomerID) {
		this.customerID = cutomerID;
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
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public boolean isCustomerIsActive() {
		return customerIsActive;
	}
	public void setCustomerIsActive(boolean customerIsActive) {
		this.customerIsActive = customerIsActive;
	}

}
