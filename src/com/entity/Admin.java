package com.entity;

public class Admin {
	protected int id;
	protected String username;
	protected String password;
	protected String address;
    protected double billamount; // Adding billamount field
    protected String billstatus; // Adding billstatus field
	
	public Admin() {
	}
	
	public Admin(String username, String password, String address, double billamount, String billstatus) {
		super();
		this.username = username;
		this.password = password;
		this.address = address;
        this.billamount = billamount;
        this.billstatus = billstatus;
	}

	public Admin(int id, String username, String password, String address, double billamount, String billstatus) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.address = address;
        this.billamount = billamount;
        this.billstatus = billstatus;
	}
	
	public Admin(int id, String username, double billamount, String billstatus) {
        super();
        this.id = id;
        this.username = username;
        this.billamount = billamount;
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
	
	public double getBillAmount() {
        return billamount;
    }

    public void setBillAmount(double billamount) {
        this.billamount = billamount;
    }

    public String getBillStatus() {
        return billstatus;
    }

    public void setBillStatus(String billstatus) {
        this.billstatus = billstatus;
    }
}
