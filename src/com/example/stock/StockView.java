package com.example.stock;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class StockView extends Activity{
	//private ImageView ivCurrent;
    private EditText etStock;
    private Button bLocation;
    //private TextView tvCondition;
    private StockDBManager s_db = new StockDBManager(this);
     ArrayList<Stock> array;
     ListworkAdapter arrayAdapter;
     ListView list;
    private static final int DELETE_WORK = Menu.FIRST;
    private static final int ABOUT = Menu.FIRST + 2;
    private final static String GOOGLE_STOCK = "http://www.google.com/ig/api?stock=";
    public final static String link = "https://www.google.com/finance/info?infotype=infoquoteall&q=";
    private Stock stock = new Stock();
    
    @SuppressLint("NewApi")
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		initialize();
	}

	
    private void initialize() {
    	array = new ArrayList<Stock>();
        arrayAdapter = new ListworkAdapter(this, R.layout.stocklist, array);
        
        etStock = (EditText) findViewById(R.id.etStock);
        list = (ListView) findViewById(R.id.list);
        //Load from DB
        List<Stock> stocks = s_db.getAllStocks(); 
        for (Stock s: stocks) {
        	stock = new Stock(s.getName(),s.getCompany(),s.getBuyPrice(),
	   	 			s.getChangeValue(),s.getPercChange(),s.getVolume(),s.getTime(),
	   	 			s.getLow(),s.getHigh(),s.getPe(),s.getMarketcap(),s.getAvg_volume());
        	array.add(0,stock);
        }
        arrayAdapter = new ListworkAdapter(this, R.layout.stocklist, array);
        arrayAdapter.notifyDataSetChanged();
        list.setAdapter(arrayAdapter);
        bLocation = (Button) findViewById(R.id.bLocation);
        //Onclick event
        OnClickListener add = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etStock.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StockView.this);
                    builder.setTitle("Info missing");
                    builder.setMessage("Please input the symbol of the stock");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub                            
                        }                        
                    });
                    builder.show();
                }
                else {
                	String location = etStock.getText().toString().toUpperCase();
                	stock = s_db.getStock(location);
                	if(stock.getName().equals("")) loadData(location);
                	else {
                		Intent i = new Intent(StockView.this, StockDetail.class);
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
	                	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                	getBaseContext().startActivity(i);
                	}
                }
            }
            	
        };
        
        bLocation.setOnClickListener(add);        
    }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, DELETE_WORK, 0,"Delete" ).setIcon(android.R.drawable.ic_delete);        
        menu.add(0, ABOUT, 0,"Update" ).setIcon(android.R.drawable.ic_menu_info_details);
        return true;
	}
	  @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		  switch (item.getItemId()) {            
          case DELETE_WORK: {
              deleteCheckedWork();
              break;
          }            
          case ABOUT: {
              /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
              builder.setTitle("NeptuneStock");
              builder.setMessage("Team:" + "\n" + " Neptune Stock " + "\n" + "SOURCE:" + "\n" + " Hedspi2013K55 "+ "\n" +"Player:" + "\n" + " phonex ");
              builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {                                    
                  public void onClick(DialogInterface dialog, int which) {
                  }
              });
              builder.setIcon(android.R.drawable.ic_dialog_info);
              builder.show();*/
        	  updateNet();
              break;
          }
      }
      return true;
	    }
	  
	  
	private void deleteCheckedWork() {
	        if (array.size() > 0) {
	            for (int i = array.size() - 1; i >= 0; i--) {
	                if (array.get(i).isChecked()) {
	                	stock = array.get(i);
	                	array.remove(i);	                    
	                    s_db.deleteStock(stock);
	                    arrayAdapter.notifyDataSetChanged();
	                    continue;
	                }
	            }
	        }        
	    }

	private void loadData(String name) throws NetworkOnMainThreadException{
		ConnectionDetector cd;
		cd = new ConnectionDetector(StockView.this);
		//AlertDialogManager alert = new AlertDialogManager();
		
		//Log.e("loi netwod12","fail");
        // Check for internet connection
        if (cd.isConnectingToInternet()==false) {
            // Internet Connection is not present
        	
        	Log.e("loi netwod","fail");
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Network error");
			builder.setMessage("Could not connect to Internet!");
			builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub      
					etStock.setText("");
				}                        
	   	 	});
			builder.show();
        }
        	
        else{	
        	//HERE
        	try{ 
    		URL url= new URL(GOOGLE_STOCK + name);
    		
    		//InputSource is = new InputSource(new StringReader("Some XML here and there"));
    	//	XPathFactory factory = XPathFactory.newInstance();
    		//XPath xPath = factory.newXPath();
    	//	xPath.setC
    		//NodeList nodeList = (NodeList) xPath.evaluate("/xpath", is, XPathConstants.NODESET);
    		
    		SAXParserFactory spf = SAXParserFactory.newInstance();
    		SAXParser sp = spf.newSAXParser();
    		XMLReader reader=sp.getXMLReader();
    		StockXmlHandler handler = new StockXmlHandler();
    		reader.setContentHandler(handler);
    		reader.parse(new InputSource(url.openStream()));
    		String stockContent=handler.getCompany();
    		//String priceContent=handler.getBuyPrice().toString();
    		String avgVolume = handler.getAvgVolume();
    		if (stockContent.equals("")) {
    			stock = new Stock("","",0f,0f,0f,0f,"",0f,0f,0f,0f,0f);
    			AlertDialog.Builder builder = new AlertDialog.Builder(this);
    			builder.setTitle("Info error");
    			builder.setMessage("Invalid symbol. Make sure the symbol that you entered is correct!");
    			builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
    				public void onClick(DialogInterface dialog, int which) {
    					// TODO Auto-generated method stub      
    					etStock.setText("");
    				}                        
		   	 	});
    			builder.show();
    		} else {	
    			Log.e("loi stock add",handler.getAvgVolume());
    			Log.e("loi stock add",handler.getAvgVolume());
//		   	 	Log.e("loi stock 123",handler.getMarketcap());
		   	 	stock = new Stock(handler.getName(),handler.getCompany(),Float.parseFloat(handler.getBuyPrice()),
		   	 			Float.parseFloat(handler.getChangeValue()),Float.parseFloat(handler.getPercChange()),Float.parseFloat(handler.getVolume()),
		   	 			handler.getTime(),Float.parseFloat(handler.getHigh()),Float.parseFloat(handler.getLow()),0f,Float.parseFloat(handler.getMarketcap()),Float.parseFloat(handler.getAvgVolume()));
		   	 	etStock.setText("");
		   	 	s_db.addStock(stock);
		   	 	array.add(0,stock);
		   	 	list.setAdapter(arrayAdapter);
    		}
    	}
    	catch (Exception e){
    		Toast.makeText(getBaseContext(), "Fail to update",Toast.LENGTH_LONG).show();     
    	}
        	
    	}
        	
    }

	public void updateNet() {

 	   ArrayList<Stock> new_array = new ArrayList<Stock>();
 	   Stock temp = new Stock();
 	   List<Stock> stocks = s_db.getAllStocks(); 
        for (Stock s: stocks) {
     	    loadData(s.getName());
        		temp = new Stock(stock.getName(),stock.getCompany(),stock.getBuyPrice(),
	   	 			stock.getChangeValue(),stock.getPercChange(),stock.getVolume(),stock.getTime(),
	   	 			stock.getLow(),stock.getHigh(),stock.getPe(),stock.getMarketcap(),stock.getAvg_volume());
        		new_array.add(0,temp);
        		s_db.updateStock(temp);
        }
        arrayAdapter = new ListworkAdapter(this, R.layout.stocklist, new_array);
        arrayAdapter.notifyDataSetChanged();
        list.setAdapter(arrayAdapter);
    }
}
