package com.example.stock;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class Owned_Stock extends Stock {

	int number;
	Float avg_buy_price;
	Float avg_sell_price;
	Float review;
	int bought;
	private HistoryDBManager h_db = new HistoryDBManager(AccManager.appContext);
	private UserDBManager u_db = new UserDBManager(AccManager.appContext);
    private StatDBManager db = new StatDBManager(AccManager.appContext);
	private Statistic stat = new Statistic();
	
	// Empty constructor
    public Owned_Stock(){
 
    }
	
	//Constructor
	public Owned_Stock(String name,String company,Float buy_price,Float change_value,Float perc_change,Float volume,String time,Float low,Float high,Float pe,
			Float marketcap,Float avg_volume,int number,Float avg_buy_price,Float avg_sell_price,Float review,int bought) {
		super(name,company,buy_price,change_value,perc_change,volume,time,low,high,pe,marketcap,avg_volume);
		this.number = number;
		this.avg_buy_price = avg_buy_price;
		this.avg_sell_price = avg_sell_price;
		this.review = review;
		this.bought = bought;
	}
	
	//get number
	public int getNumber() {
		return this.number;
	}
			
	//set number
	public void setNumber(int number) {
		this.number = number;
	}
	
	//get avg_buy_price
	public Float getAvgBuyPrice() {
		return this.avg_buy_price;
	}
			
	//set avg_buy_price
	public void setAvgBuyPrice(Float avg_buy_price) {
		this.avg_buy_price = avg_buy_price;
	}
	
	//get avg_sell_price
	public Float getAvgSellPrice() {
		return this.avg_sell_price;
	}
				
	//set avg_sell_price
	public void setAvgSellPrice(Float avg_sell_price) {
		this.avg_sell_price = avg_sell_price;
	}
	
	//get review
	public Float getReview() {
		return this.review;
	}
				
	//set review
	public void setReview(Float review) {
		this.review = review;
	}
	
	//set number
	public void setBought(int bought) {
		this.bought = bought;
	}
		
	//get avg_buy_price
	public int getBought() {
		return this.bought;
	}
	
	public void buy(int num,Float price) {

		//Tinh lai avg, them vao History va miniHistory, cap nhat userMoney		
		//ADD VAO HISTORY
		Float thu = 0f;
		Float von = 0f;
		Date cDate = new Date();
		String date = new SimpleDateFormat("dd-MM-yyyy").format(cDate);
		History h = new History(date,this.getName(),"buy",num,price);
		h_db.addHistory(h);
		//Tinh avg,review,number
		this.setNumber(this.getNumber()+num);
		this.setAvgBuyPrice(h_db.calculate(this.getName(), "buy"));
		this.setAvgSellPrice(h_db.calculate(this.getName(), "sell"));
		if(this.getBought()!=0) {
			von = this.getAvgBuyPrice()*(this.getNumber()+this.getBought());
			thu = this.getAvgSellPrice()*this.getBought();
			Float delta = thu/von-1;
			this.setReview(delta*100);
		}		
		User user = u_db.getUser();
		user.setMoney(user.getMoney()-price*num);
		u_db.updateUser(user);
		stat = new Statistic(this.getName(),thu-von,this.getAvgBuyPrice(),this.getAvgSellPrice());
		if(db.check(this.getName())==0) db.addStat(stat);
		else db.updateStat(stat);
	}
	
	public void sell(int num,Float price) {	

		Float thu = 0f;
		Float von = 0f;
		//ADD VAO HISTORY
		Date cDate = new Date();
		String time = new SimpleDateFormat("dd-MM-yyyy").format(cDate);
		History h = new History(time,this.getName(),"sell",num,price);
		h_db.addHistory(h);
		this.setNumber(this.getNumber()-num);
		this.setBought(this.getBought()+num);
		Log.e("sold",Integer.toString(this.getBought()));
		this.setAvgBuyPrice(h_db.calculate(this.getName(), "buy"));
		this.setAvgSellPrice(h_db.calculate(this.getName(), "sell"));
		von = this.getAvgBuyPrice()*(this.getNumber()+this.getBought());
		thu = this.getAvgSellPrice()*this.getBought();
		Float delta = thu/von-1;
		this.setReview(delta*100);
		User user = u_db.getUser();
		user.setMoney(user.getMoney()+price*num);
		u_db.updateUser(user);
		stat = new Statistic(this.getName(),thu-von,this.getAvgBuyPrice(),this.getAvgSellPrice());
		if(db.check(this.getName())==0) db.addStat(stat);
		else db.updateStat(stat);
	}
}