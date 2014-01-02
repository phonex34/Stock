package com.example.stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomViewGroup extends LinearLayout{

	public CheckBox cb;
	public TextView stockName;
	public TextView stockBuyPrice;
	public TextView stockCompany;
	public TextView stockChangeValue;
	public ImageView imageTriangle;
	
	public CustomViewGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		//Sử dụng LayoutInflater để gán giao diện trong list.xml cho class này [/COLOR]
        LayoutInflater li = (LayoutInflater) this.getContext()
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.stocklist, this, true);
        
        //Lấy về các View qua Id
        cb = (CheckBox) findViewById(R.id.check_stock);
        stockName = (TextView) findViewById(R.id.stockName);
        stockCompany = (TextView) findViewById(R.id.stockCompany);
        stockBuyPrice = (TextView) findViewById(R.id.stockBuyPrice);
        stockChangeValue =(TextView) findViewById(R.id.stockChangePrice);
        imageTriangle=(ImageView) findViewById(R.id.imageTriangle);
	}
	
}
