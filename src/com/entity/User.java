package com.entity;

public class User {
	private int id;
	private String username;
	private String password;
	private String address;
	protected double billamount; 
    protected String billstatus; 

	public User() {
		super();
	}

	public User(int id, String username, String password, String address) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.address  = address;
	}
	public User(int id, String username, double billamount, String billstatus) {
        super();
        this.id = id;
        this.username = username;
        this.billamount = billamount;
        this.billstatus = billstatus;
	}

	public double getBillamount() {
		return billamount;
	}

	public void setBillamount(double billamount) {
		this.billamount = billamount;
	}

	public String getBillstatus() {
		return billstatus;
	}

	public void setBillstatus(String billstatus) {
		this.billstatus = billstatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
