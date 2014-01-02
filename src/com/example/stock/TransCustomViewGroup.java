package com.example.stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TransCustomViewGroup extends LinearLayout {

	public TextView name;
	public TextView avgbuy;
	public TextView avgsell;
	public TextView review;
	
	public TransCustomViewGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
        LayoutInflater li = (LayoutInflater) this.getContext()
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        li.inflate(R.layout.transaction, this, true);
        
        //cb = (CheckBox) findViewById(R.id.check_stock);
        name = (TextView) findViewById(R.id.stockName);
        avgbuy = (TextView) findViewById(R.id.avgbuy);
        avgsell = (TextView) findViewById(R.id.avgsell);
        review =(TextView) findViewById(R.id.review);
	}
	
}

