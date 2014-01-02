package com.example.stock;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


@SuppressWarnings("deprecation")
public class Main extends TabActivity{
	  /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost();
         
        // Tab for Photos
        TabSpec stockview = tabHost.newTabSpec("StockView");
        // setting Title and Icon for the Tab
        stockview.setIndicator("StockView", getResources().getDrawable(R.layout.icon_stockview_tab));
        Intent stockviewIntent = new Intent(this,StockView.class);
        stockview.setContent(stockviewIntent);
         
        // Tab for Songs
        TabSpec accspec = tabHost.newTabSpec("AccManager");       
        accspec.setIndicator("AccManager", getResources().getDrawable(R.layout.icon_accmanager_tab));
        Intent accIntent = new Intent(this, AccManager.class);
        accspec.setContent(accIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(stockview); // Adding photos tab
        tabHost.addTab(accspec); // Adding songs tab
    }

}