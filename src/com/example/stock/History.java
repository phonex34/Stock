package com.example.stock;

public class History {
	
	String time;
	String name;
	String type;
	int number;
	float price;
	
	public History() {
		
	}
	
	public History(String time,String name,String type,int number,float price) {
		
		this.time = time;
		this.name = name;
		this.type = type;
		this.number = number;
		this.price = price;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
}