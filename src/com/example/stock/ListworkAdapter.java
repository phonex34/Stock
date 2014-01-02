package com.example.stock;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ListworkAdapter extends ArrayAdapter<Stock> {

	ArrayList<Stock> array;
	int resource;
	Context context;
	public ListworkAdapter(Context context, int textViewResourceId,ArrayList<Stock> objects) 
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
	            stockView = new CustomViewGroup(getContext());
	        }
	        
	        //Lấy về đối tượng Work hiện tại
	        final Stock stock = array.get(position);

	        if (stock != null) {
	        	TextView stockName = ((CustomViewGroup) stockView).stockName;
	            TextView stockBuyPrice = ((CustomViewGroup) stockView).stockBuyPrice;
	            TextView stockChangeValue = ((CustomViewGroup) stockView).stockChangeValue;
	            TextView stockCompany = ((CustomViewGroup) stockView).stockCompany;
	            CheckBox checkStock = ((CustomViewGroup) stockView).cb;
	            ImageView imageTriangle =((CustomViewGroup) stockView).imageTriangle;
	            //Set sự kiện khi đánh dấu vào checkbox trên list
	            checkStock.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	            	 @Override
	                public void onCheckedChanged(CompoundButton buttonView,
	                        boolean isChecked) {
	                    stock.setChecked(isChecked);                    
	                }				          
	            });
	            
	            
		           //Lấy về nội dung cho TextView và CheckBox dựa vào đối tượng Work hiện tại[/COLOR]
		            stockName.setText(stock.getName());
		            stockBuyPrice.setText(stock.getBuyPrice().toString());
		            //stockBuyPrice.setTextColor(Color.RED);
		            stockCompany.setText(stock.getCompany());
		            
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
		            	
		            checkStock.setChecked(stock.isChecked());
		            stockView.setOnClickListener(new OnClickListener()
		            {
		                @Override
		                public void onClick(View v) {
		                
		                	Intent i = new Intent(getContext(), StockDetail.class);
		                	String stock_name=stock.getName();
		                	String stock_BuyPrice=stock.getBuyPrice().toString();
		                	String stock_company= stock.getCompany();
		                	String stock_change=stock.getChangeValue().toString();
		                	String stock_perchange=stock.getPercChange().toString();
		                	String stock_volume=Float.toString(stock.getVolume());
		                	String stock_high=Float.toString(stock.getHigh());
		                	String stock_low=Float.toString(stock.getLow());
		                	String stock_marcap =Float.toString(stock.getMarketcap());
		                	String stock_avgvolume =Float.toString(stock.getAvg_volume());
		                	String stock_time =stock.getTime();
		                	//	String stock_high=stock.ge
		                	i.putExtra("stock_name", stock_name);
		                	i.putExtra("stock_BuyPrice", stock_BuyPrice);
		                	i.putExtra("stock_company", stock_company);
		                	i.putExtra("stock_change", stock_change);
		                	i.putExtra("stock_perchange", stock_perchange);
		                	i.putExtra("stock_volume", stock_volume);
		                	i.putExtra("stock_high", stock_high);
		                	i.putExtra("stock_low", stock_low);
		                	i.putExtra("stock_marcap", stock_marcap);
		                	i.putExtra("stock_avgvolume", stock_avgvolume);
		                	i.putExtra("stock_time", stock_time);
		                	getContext().startActivity(i);
			    			
		                	//i.putExtra("stock_volume", stock_volume);
		                	//i.putExtra("stock_company", st
		                	/*backup	String stock_name="dcm";
		                	i.putExtra("stock_name", stock.getName());
		                	
		                	getContext().startActivity(i);
		    					*/		
		                }
		            });
	        }        
	        return stockView;
	}
}
