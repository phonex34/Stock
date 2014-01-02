package com.example.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class HistoryView extends Activity {
	
	private HistoryDBManager h_db = new HistoryDBManager(AccManager.appContext);
	TableLayout table;

	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
		// get a reference for the TableLayout
	    table = (TableLayout) findViewById(R.id.table);
	    TableRow tr_head = new TableRow(this);
	    tr_head.setId(10);
	    tr_head.setBackgroundColor(Color.GRAY);
	    tr_head.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	    
	    TextView label_date = new TextView(this);
        label_date.setId(20);
        label_date.setText("DATE");
        label_date.setPadding(30, 5, 5, 0);
        tr_head.addView(label_date);// add the column to the table row here

        TextView label_weight_kg = new TextView(this);
        label_weight_kg.setId(21);// define id that must be unique
        label_weight_kg.setText("Name"); // set the text for the header 
        label_weight_kg.setPadding(5, 5, 5, 0); // set the padding (if required)
        tr_head.addView(label_weight_kg); // add the column to the table row here
        
        TextView type= new TextView(this);
        type.setId(22);
        type.setText("Type");
        type.setPadding(0, 5, 5, 10);
        tr_head.addView(type);// add the column to the table row here
                
        TextView number= new TextView(this);
        number.setId(23);
        number.setText("Number");
        number.setPadding(5, 5, 5, 5);
        tr_head.addView(number);// add the column to the table row here
                
        TextView price= new TextView(this);
        price.setId(24);
        price.setText("Price");
        price.setPadding(5, 0, 5, 5);
        tr_head.addView(price);// add the column to the table row here
        
        table.addView(tr_head, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	    
	    List<History>  list = h_db.getAllHistory(); 
	    ListIterator<History> li = list.listIterator(list.size());
	    List<History> List = new ArrayList<History>();
	    // Iterate in reverse.
	    while(li.hasPrevious()) {
	    	List.add(li.previous());
	    }	    
	    for (History temp : List) {
	    	TableRow tr = new TableRow(this);
	    	tr.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        	//Toast.makeText(getBaseContext(),"Time: "+temp.getTime()+":"+temp.getType()+" "+temp.getNumber(),Toast.LENGTH_LONG).show();
	    	TextView labelDATE = new TextView(this);
	    	labelDATE.setText(temp.getTime());
	    	labelDATE.setPadding(0, 5, 5, 5);
	    	tr.addView(labelDATE);
	    	TextView labelWEIGHT = new TextView(this);
	    	labelWEIGHT.setText(temp.getName());
	    	tr.addView(labelWEIGHT);
	    	TextView TYPE = new TextView(this);
	    	TYPE.setText(temp.getType());
	    	tr.addView(TYPE);
	    	TextView NUMBER = new TextView(this);
	    	NUMBER.setText(Integer.toString(temp.getNumber()));
	    	NUMBER.setPadding(25, 5, 5, 5);
	    	tr.addView(NUMBER);
	    	TextView PRICE = new TextView(this);
	    	PRICE.setText(Float.toString(temp.getPrice()));
	    	PRICE.setPadding(5, 0, 5, 5);
	    	tr.addView(PRICE);
	    	table.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        }
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
    	menu.add(0, 1, 0,"Delete all history" );
    	return true;
    }
    	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	  		
    	switch (item.getItemId()) {            
    		case 1: {
    			h_db.deleteAllHistory();
    			Intent intent = getIntent();
    			finish();
    			startActivity(intent);
    			break;
    		}              		
    	}
    	return true;
    }
}