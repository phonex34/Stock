package com.example.stock;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OwnedListAdapter extends ArrayAdapter<Owned_Stock> {

	ArrayList<Owned_Stock> array;
	int resource;
	Context context;
	public OwnedListAdapter(Context context, int textViewResourceId,ArrayList<Owned_Stock> objects) 
	{
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		 this.context = context;
	     resource = textViewResourceId;
	     array = objects; 
	}
	 @Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
		  View stockView = convertView;
	        
	        if (stockView == null) {
	            stockView = new OwnCustomViewGroup(getContext());
	        }
	        
	        //Láº¥y vá»� Ä‘á»‘i tÆ°á»£ng Work hiá»‡n táº¡i
	        final Owned_Stock stock = array.get(position);

	        if (stock != null) {
	        	TextView stockName = ((OwnCustomViewGroup) stockView).stockName;
	            TextView stockBuyPrice = ((OwnCustomViewGroup) stockView).stockBuyPrice;
	            TextView stockChangeValue = ((OwnCustomViewGroup) stockView).stockChangeValue;
	            TextView stockCompany = ((OwnCustomViewGroup) stockView).stockCompany;
	            TextView stockNumber = ((OwnCustomViewGroup) stockView).stockNumber;
	            ImageView imageTriangle =((OwnCustomViewGroup) stockView).imageTriangle;	            
	            
	            
		           //Láº¥y vá»� ná»™i dung cho TextView vÃ  CheckBox dá»±a vÃ o Ä‘á»‘i tÆ°á»£ng Work hiá»‡n táº¡i[/COLOR]
		            stockName.setText(stock.getName());
		            stockBuyPrice.setText(stock.getBuyPrice().toString());
		            stockCompany.setText(stock.getCompany());
		            stockNumber.setText("Num: "+Integer.toString(stock.getNumber()));
		            
		            if(stock.getChangeValue()<0)
		            {
		            	stockChangeValue.setText(stock.getChangeValue().toString());
		            	stockChangeValue.setTextColor(Color.RED);
		            	imageTriangle.setImageResource(R.drawable.redtriangle);
		            }
		            else if(stock.getChangeValue()>0)
		            {
		            	stockChangeValue.setText("+"+stock.getChangeValue().toString());
		            	stockChangeValue.setTextColor(Color.parseColor("#006600"));
		            	imageTriangle.setImageResource(R.drawable.greentriangle);
		            }
		            else if(stock.getChangeValue()==0)
		            {
		            	stockChangeValue.setText(stock.getChangeValue().toString());
		            	stockChangeValue.setTextColor(Color.YELLOW);
		            }
		            	
		          ///  checkStock.setChecked(stock.isChecked());
		            stockView.setOnClickListener(new OnClickListener()
		            {
		                @Override
		                public void onClick(View v) {
		                
		                	Intent i = new Intent(getContext(), OwnStockDetail.class);
		                	String stock_name=stock.getName();
		                	String stock_BuyPrice=stock.getBuyPrice().toString();
		                	String stock_company= stock.getCompany();
		                	String stock_change=stock.getChangeValue().toString();
		                	String stock_perchange=stock.getPercChange().toString();
		                	String stock_number=Integer.toString(stock.getNumber());
		                	String stock_volume=Float.toString(stock.getVolume());
		                	String stock_high=Float.toString(stock.getHigh());
		                	String stock_low=Float.toString(stock.getLow());
		                	String stock_marcap =Float.toString(stock.getMarketcap());
		                	String stock_avgvolume =Float.toString(stock.getAvg_volume());
		                	String stock_time =stock.getTime();
		                	String stock_avgbuy = Float.toString(stock.getAvgBuyPrice());
		                	String stock_avgsell = Float.toString(stock.getAvgSellPrice());
		                	String stock_review = String.format("%.2f",stock.getReview());
		                	String stock_bought = Integer.toString(stock.getBought());
		                	//	String stock_volume=Integer.toString(stock.getVolume());
		                	i.putExtra("stock_name", stock_name);
		                	i.putExtra("stock_BuyPrice", stock_BuyPrice);
		                	i.putExtra("stock_company", stock_company);
		                	i.putExtra("stock_change", stock_change);
		                	i.putExtra("stock_perchange", stock_perchange);
		                	i.putExtra("stock_number", stock_number);
		                	i.putExtra("stock_volume", stock_volume);
		                	i.putExtra("stock_high", stock_high);
		                	i.putExtra("stock_low", stock_low);
		                	i.putExtra("stock_marcap", stock_marcap);
		                	i.putExtra("stock_avgvolume", stock_avgvolume);
		                	i.putExtra("stock_time", stock_time);
		                	i.putExtra("stock_avgbuy", stock_avgbuy);
		                	i.putExtra("stock_avgsell", stock_avgsell);
		                	i.putExtra("stock_review", stock_review);
		                	i.putExtra("stock_bought",stock_bought);
		                	
		                	
		                	getContext().startActivity(i);
		                }
		            });
	        }        
	        return stockView;
	}
}
