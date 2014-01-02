package com.example.stock;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TransAdapter  extends ArrayAdapter<Statistic> {

	ArrayList<Statistic> array;
	int resource;
	Context context;
	public TransAdapter(Context context, int textViewResourceId,ArrayList<Statistic> objects) 
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
	            stockView = new TransCustomViewGroup(getContext());
	        }
	        
	        final Statistic stat = array.get(position);

	        if (stat != null) {
	        	TextView stockName = ((TransCustomViewGroup) stockView).name;
	            TextView stockAvgBuy = ((TransCustomViewGroup) stockView).avgbuy;
	            TextView stockAvgSell = ((TransCustomViewGroup) stockView).avgsell;
	            TextView stockReview = ((TransCustomViewGroup) stockView).review;           
	            
	           
	            stockName.setText(stat.getName());
	            stockAvgBuy.setText("Avg buy: "+stat.getAvgBuy().toString());
	            stockAvgSell.setText("Avg sell: "+stat.getAvgSell().toString());
	            stockReview.setText(stat.getDelta().toString());
	            
	            if(stat.getDelta()<0)
	            {
	            	stockReview.setTextColor(Color.RED);
	            }
	            else if(stat.getDelta()>0)
	            {
	            	stockReview.setTextColor(Color.parseColor("#006600"));
	            }
	            else
	            {
	            	stockReview.setTextColor(Color.YELLOW);
	            }
		            
	        }        
	        return stockView;
	}
}
