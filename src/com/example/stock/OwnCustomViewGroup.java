package com.example.stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OwnCustomViewGroup extends LinearLayout{

	//public CheckBox cb;
	public TextView stockName;
	public TextView stockBuyPrice;
	public TextView stockCompany;
	public TextView stockChangeValue;
	public TextView stockNumber;
	public ImageView imageTriangle;
	
	public OwnCustomViewGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
        LayoutInflater li = (LayoutInflater) this.getContext()
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        li.inflate(R.layout.accitem, this, true);
        
        //cb = (CheckBox) findViewById(R.id.check_stock);
        stockName = (TextView) findViewById(R.id.stockName);
        stockCompany = (TextView) findViewById(R.id.stockCompany);
        stockBuyPrice = (TextView) findViewById(R.id.stockBuyPrice);
        stockChangeValue =(TextView) findViewById(R.id.stockChangePrice);
        stockNumber = (TextView) findViewById(R.id.stockNumber);
        imageTriangle=(ImageView) findViewById(R.id.imageTriangle);
	}
	
}
