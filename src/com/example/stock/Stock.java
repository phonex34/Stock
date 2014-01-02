package com.example.stock;

public class Stock {
	private String name;
	private String company;
	private Float buy_price;
	private Float change_value;
	private Float perc_change;
	private Float volume;
	private String time;
	private Boolean isChecked;
	private Float low;
	private Float high;
	private Float pe;
	private Float market_cap;
	private Float avg_volume;
    // Empty constructor
   
	public Stock() {
 
    }
	
	//Constructor
	public Stock(String name,String company,Float buy_price,Float change_value,Float perc_change,Float volume,String time,Float low,Float high,Float pe,Float market_cap,Float avg_volume) {
		this.name = name;
		this.company = company;
		this.buy_price = buy_price;
		this.change_value = change_value;
		this.perc_change = perc_change;
		this.volume = volume;
		this.time = time;
		this.high= high;
		this.low=low;
		this.pe=pe;
		this.isChecked = false;
		this.avg_volume = avg_volume;
		this.market_cap= market_cap;
}
	
	public Stock(String symbol,String last,String company,String change_value)
	{
		this.change_value = Float.parseFloat(change_value);
		this.name = symbol;
		this.buy_price  = Float.parseFloat(last);
		this.company = company;
		isChecked = false;
	}
	//get name
	public String getName() {
		return this.name;
	}
	
	//set name
	public void setName(String name) {
		this.name = name;
	}

	//get company
	public String getCompany() {
		return this.company;
	}
		
	//set company
	public void setCompany(String company) {
		this.company = company;
	}
	
	//get time
	public String getTime() {
		return this.time;
	}
		
	//set time
	public void setTime(String time) {
		this.time = time;
	}	
	
	//get buy_price
	public Float getBuyPrice() {
		return this.buy_price;
	}
		
	//set buy_price
	public void setBuyPrice(Float buy_price) {
		this.buy_price = buy_price;
	}
	
	//get change_value
	public Float getChangeValue() {
		return this.change_value;
	}
		
	//set change_value
	public void setChangeValue(Float change_value) {
		this.change_value = change_value;
	}	
	
	//get perc_change
	public Float getPercChange() {
		return this.perc_change;
	}
			
	//set perc_change
	public void setPercChange(Float perc_change) {
		this.perc_change = perc_change;
	}	
	
	
	//get volume
	public Float getVolume() {
		return this.volume;
	}
			
	//set perc_change
	public void setVolume(Float volume) {
		this.volume = volume;
	}
	
	public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
	}
    
	public boolean isChecked() {
        return isChecked;
	}

	public void setHigh(Float high) {
		this.high = high;
	}
	
	public Float getHigh() {
		return this.high;
	}
	
	public void setLow(Float low) {
		this.low = low;
	}	
	
	public Float getLow() {
		return this.low;
	}
	
	public void setPe(Float pe) {
		this.pe = pe;
	}	
	
	public Float getPe() {
		return this.pe;
	}
	
	public void setMarketcap(Float market_cap) {
		this.market_cap = market_cap;
	}
	
	//get marketcap
	public Float getMarketcap() {
		return this.market_cap;
	}
	
	public void setAvg_volume(Float avg_volume) {
		this.avg_volume = avg_volume;
	}
	
	//get avg volume_value
	public Float getAvg_volume() {
		return this.avg_volume;
	}
}
