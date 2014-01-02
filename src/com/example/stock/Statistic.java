package com.example.stock;

public class Statistic {
	
	String name;
	Float delta;
	Float AvgBuy;
	Float AvgSell;
	
	public Statistic() {
		 
    }
	
	//Constructor
	public Statistic(String name,Float delta,Float AvgBuy,Float AvgSell) {
		this.name = name;
		this.delta = delta;
		this.AvgBuy = AvgBuy;
		this.AvgSell = AvgSell;
	}
	
	//get name
	public String getName() {
		return this.name;
	}
		
	//set name
	public void setName(String name) {
		this.name = name;
	}
	
	public Float getDelta() {
		return this.delta;
	}
		
	public void setDelta(Float delta) {
		this.delta = delta;
	}	
	
	public Float getAvgBuy() {
		return this.AvgBuy;
	}
		
	public void setAvgBuy(Float AvgBuy) {
		this.AvgBuy = AvgBuy;
	}	
	
	public Float getAvgSell() {
		return this.AvgSell;
	}
		
	public void setAvgSell(Float AvgSell) {
		this.AvgSell = AvgSell;
	}	
}
