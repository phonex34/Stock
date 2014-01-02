package com.example.stock;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class Transaction extends Activity {

	private StatDBManager db = new StatDBManager(this);
	Statistic stat = new Statistic();
    ListView list;
    ArrayList<Statistic> array;
    TransAdapter arrayAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.translist);
        array = new ArrayList<Statistic>();
        TextView row1 = (TextView) findViewById(R.id.row1);
        row1.setBackgroundColor(Color.GRAY);
        list = (ListView) findViewById(R.id.list);
        List<Statistic> stats = db.getAllStats(); 
        for (Statistic s: stats) {
        	stat = new Statistic(s.getName(),s.getDelta(),s.getAvgBuy(),s.getAvgSell());
        	array.add(0,stat);
        }	
        arrayAdapter = new TransAdapter(this, R.layout.transaction, array);
        arrayAdapter.notifyDataSetChanged();
        list.setAdapter(arrayAdapter);
	}
}
