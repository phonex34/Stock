package com.example.stock;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StockXmlHandler extends DefaultHandler {

	private String name;
	private String time;
	private String buy_price;
	private String change_value;
	private String company;
	private String currency;
	private String volume;
	private String perc_change;
	private String condition;
	private String icon;
	private String avg_volume;
	private String high;
	private String low;
	private String marketcap;
	

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("trade_timestamp")) {
			setTime(attributes.getValue("data"));
		} else if (localName.equals("currency")) {
			setCurrency(attributes.getValue("data"));
		} else if (localName.equals("company")) {
			setCompany(attributes.getValue("data"));
		} else if (localName.equals("symbol")) {
			setName(attributes.getValue("data"));
		}	else if (localName.equals("last")) {
			setBuyPrice(attributes.getValue("data"));
		} 	else if (localName.equals("change")) {
			setChangeValue(attributes.getValue("data"));
		} 	else if (localName.equals("volume")) {
			setVolume(attributes.getValue("data"));
		} 	else if (localName.equals("perc_change")) {
			setPercChange(attributes.getValue("data"));
		} 	else if (localName.equals("avg_volume")) {
			setAvgVolume(attributes.getValue("data"));
		} 	else if (localName.equals("high")) {
			setHigh(attributes.getValue("data"));
		} 	else if (localName.equals("low")) {
			setLow(attributes.getValue("data"));
		} 	else if (localName.equals("market_cap")) {
			setMarketcap(attributes.getValue("data"));
		} 
	}



	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBuyPrice() {
		return buy_price;
	}

	public void setBuyPrice(String buy_price) {
		this.buy_price = buy_price;
	}
	
	public String getChangeValue() {
		return change_value;
	}

	public void setChangeValue(String change_value) {
		this.change_value = change_value;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	public String getPercChange() {
		return perc_change;
	}

	public void setPercChange(String perc_change) {
		this.perc_change = perc_change;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getAvgVolume() {
		 return this.avg_volume;
	}


	public void setAvgVolume(String avg_volume) {
		//Log.e("loi o hree",avg_volume);
		if(avg_volume.length()==0)
		{
			avg_volume="0f";
			this.avg_volume=avg_volume;
			}
		else 
		{
			this.avg_volume=avg_volume;
		}
		//Log.e("loi o hree2",avg_volume);
	}
	
	public String getMarketcap() {
		 return this.marketcap;
	}
	

	public void setMarketcap(String marketcap) {
		//Log.e("loi o hree",marketcap);
		if(marketcap.length()==0)
		{
			marketcap="0f";
			this.marketcap=marketcap;
			}
		else 
		{
			this.marketcap = marketcap;
		}
		//Log.e("loi o hree2",marketcap);
	}
	public String getHigh() {
		 return this.high;
	}
	
	public void setHigh(String high) {
		
		if(high.length()==0)
		{
			high="0f";
			this.high = high;
			}
		else 
		{
			this.high = high;
		}
	}

	public String getLow() {
		 return this.low;
	}
	
	public void setLow(String low) {
		
		if(low.length()==0)
		{
			low="0f";
			this.low = low;
			}
		else 
		{
			this.low = low;
		}
	}
}