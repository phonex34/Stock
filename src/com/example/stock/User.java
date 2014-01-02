package com.example.stock;

public class User {

	private String pass;
	private Float money;
	private Float first;
	private boolean active;
	
	public User() {

	}

	public User(String pass,Float money,Float first,Boolean active) {
		this.pass = pass;
		this.money = money;
		this.first = first;
		this.active = active;
	}
	
	//get pass
	public String getPass() {
		return this.pass;
	}
		
	//set name
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	//get money
	public Float getMoney() {
		return this.money;
	}
			
	//set money
	public void setMoney(Float money) {
		this.money = money;
	}
	
	//get money
	public Float getFirst() {
		return this.first;
	}
				
		//set money
	public void setFirst(Float first) {
		this.first = first;
	}
	
	//get active
	public boolean getActive() {
		return this.active;
	}
			
	//set active
	public void setActive(Boolean active) {
		this.active = active;
	}

}
