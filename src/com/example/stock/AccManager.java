package com.example.stock;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//import org.json.JSONArray;
//import org.json.JSONObject;
//
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AccManager extends Activity {
	
	private TextView tv;
	private EditText etStock;
    private Button bLocation;
    static Context appContext;
    public final static String GOOGLE_STOCK = "http://www.google.com/ig/api?stock=";
    public final static String link = "https://www.google.com/finance/info?infotype=infoquoteall&q=";
    //Cac database
    private OwnStockDBManager s_db = new OwnStockDBManager(this);
    private UserDBManager u_db = new UserDBManager(this);
    private HistoryDBManager h_db = new HistoryDBManager(this);
    private StatDBManager db = new StatDBManager(this);
    private User user = new User();
    private Owned_Stock stock = new Owned_Stock();
    ListView list;
    ArrayList<Owned_Stock> array;
    OwnedListAdapter arrayAdapter;
    static public int check=0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.acc);
        appContext=getApplicationContext();
        //u_db.addUser(new User("1234",999999f,false));
        //Check user's status
        user=u_db.getUser();
        if(user.getPass().equals("")) {   
        	//UserTable blank
        	//Need to register
        	register(); 
        } else {    
        	//Offline
        	//Check password
        	user = u_db.getUser();
        	if (!user.getActive()) {
        		login();
        	}
        }
        array = new ArrayList<Owned_Stock>();
        list = (ListView) findViewById(R.id.acclist);
        tv = (TextView) findViewById(R.id.money);
        etStock = (EditText) findViewById(R.id.etStock);
        bLocation = (Button) findViewById(R.id.bLocation);
        tv.setText("$"+Float.toString(user.getMoney()));
        List<Owned_Stock> stocks = s_db.getAllStocks(); 
        for (Owned_Stock s: stocks) {
        	stock = new Owned_Stock(s.getName(),s.getCompany(),s.getBuyPrice(),
	   	 			s.getChangeValue(),s.getPercChange(),s.getVolume(),s.getTime(),
	   	 			s.getLow(),s.getHigh(),s.getPe(),s.getMarketcap(),s.getAvg_volume(),
	   	 			s.getNumber(),s.getAvgBuyPrice(),s.getAvgSellPrice(),s.getReview(),s.getBought());
        	array.add(0,stock);
        }	
        arrayAdapter = new OwnedListAdapter(this, R.layout.accitem, array);
        arrayAdapter.notifyDataSetChanged();
        list.setAdapter(arrayAdapter);
        
        //Onclick button
        OnClickListener add = new OnClickListener() {
        	
            public void onClick(View v) {
                if (etStock.getText().toString().equals("")) {
                    //Invalid symbol
                	AlertDialog.Builder builder = new AlertDialog.Builder(AccManager.this);
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
                	//not null symbol 
                	String name = etStock.getText().toString().toUpperCase();
                	//Check in DB if existed
                	stock = s_db.getStock(name);
                	//loadData(name);
                	if(stock.getName().equals("")) {
                		//Not found
                		//Find on internet
                		loadData(name);
                		if(stock.getName().equals("")) {
                			//Invalid symbol
                			
                		} else if(stock.getName().equals("index")) {
                			//Got an index 
                			//Do nothing but show the dialog 
                		} else {
                			quickBuy(name);
                			user=u_db.getUser();
                			tv.setText("$"+Float.toString(user.getMoney()));
                		} 
                	} else {
                			//Found, make the way to detail page
                			Intent i = new Intent(AccManager.this, OwnStockDetail.class);
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
		                	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		                	getBaseContext().startActivity(i);
                	}
                }
            }
        };
        bLocation.setOnClickListener(add);       
    }
    
    @Override
    public void onResume() {
    	
    	 user=u_db.getUser();
         if(user.getPass().equals("")&(check==1)) {   
         	//UserTable blank
         	//Need to register
         	register(); 
         	check=0;
         } 
    	//tao moi cac bien
    	array = new ArrayList<Owned_Stock>();
  	   	List<Owned_Stock> stocks = s_db.getAllStocks(); 
  	   	//duyet tat ca stock
	  	 for (Owned_Stock s: stocks) {
	      	stock = new Owned_Stock(s.getName(),s.getCompany(),s.getBuyPrice(),
		   	 			s.getChangeValue(),s.getPercChange(),s.getVolume(),s.getTime(),
		   	 			s.getLow(),s.getHigh(),s.getPe(),s.getMarketcap(),s.getAvg_volume(),
		   	 			s.getNumber(),s.getAvgBuyPrice(),s.getAvgSellPrice(),s.getReview(),s.getBought());
	      	array.add(0,stock);
	     }
         arrayAdapter = new OwnedListAdapter(this, R.layout.accitem, array);
         arrayAdapter.notifyDataSetChanged();
         list.setAdapter(arrayAdapter);
         user = u_db.getUser();
         tv.setText("$"+Float.toString(user.getMoney()));
         super.onResume();   
    }
    
    //Doc xml (nhu cua chu), co them lenh add vao DB
    private void loadData(String name) throws NetworkOnMainThreadException{
    	
    	ConnectionDetector cd;
		cd = new ConnectionDetector(AccManager.this);
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
        	
        else {	
        	
    	try{ 
    		URL url= new URL(GOOGLE_STOCK + name);
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
    			stock = new Owned_Stock("","",0f,0f,0f,0f,"",0f,0f,0f,0f,0f,0,0f,0f,0f,0);
    			AlertDialog.Builder builder = new AlertDialog.Builder(AccManager.this);
    			builder.setTitle("Info error");
    			builder.setMessage("Invalid symbol. Make sure the symbol that you entered is correct!");
    			builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
    				public void onClick(DialogInterface dialog, int which) {
    					// TODO Auto-generated method stub      
    					etStock.setText("");
    				}                        
		   	 	});
    			builder.show();
    		}
    		else if (avgVolume.equals("0f")) {
    			stock = new Owned_Stock("index","",0f,0f,0f,0f,"",0f,0f,0f,0f,0f,0,0f,0f,0f,0);
    			AlertDialog.Builder builder = new AlertDialog.Builder(AccManager.this);
    			builder.setTitle("Info error");
    			builder.setMessage("Please input a stock symbol, not a index symbol!");
    			builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub      
    				etStock.setText("");
    				}                        
    			});
    			builder.show();
    		} else {	
		   	 	stock = new Owned_Stock(handler.getName(),handler.getCompany(),Float.parseFloat(handler.getBuyPrice()),
		   	 			Float.parseFloat(handler.getChangeValue()),Float.parseFloat(handler.getPercChange()),Float.parseFloat(handler.getVolume()),
		   	 			handler.getTime(),Float.parseFloat(handler.getLow()),Float.parseFloat(handler.getHigh()),0f,Float.parseFloat(handler.getMarketcap()),
		   	 			Float.parseFloat(handler.getAvgVolume()),0,0f,0f,0f,0);
		   	 	etStock.setText("");
    		}
    	}
    	catch (Exception e){
    		Toast.makeText(getBaseContext(), "Fail to update!",Toast.LENGTH_LONG).show();     
	    }
        }
    }
       
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
    	menu.add(0, 1, 0,"Logout" );        
    	menu.add(0, 2, 1,"User Control" );
    	menu.add(0, 3, 2,"History");
    	menu.add(0, 4, 3,"Update");
    	menu.add(0, 5, 4,"Transaction List");
    	menu.add(0, 6, 5, "Export data");
    	return true;
    }
    	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	  		
    	switch (item.getItemId()) {            
    		case 1: {
    			logout();
    			break;
    		}            
    		case 2: {
    			super.onOptionsItemSelected(item);
    			this.closeOptionsMenu();
    			Intent nextScreen = new Intent(AccManager.this, UserControl.class);
    			this.startActivity(nextScreen);
    			break;
    		}
    		case 3: {
    			super.onOptionsItemSelected(item);
    			this.closeOptionsMenu();
    			Intent nextScreen = new Intent(AccManager.this, HistoryView.class);
    			this.startActivity(nextScreen);
    			break;
    		}
    		case 4: {
    			updateNet();
    			break;
	        }
    		case 5: {
    			super.onOptionsItemSelected(item);
    			this.closeOptionsMenu();
    			Intent nextScreen = new Intent(AccManager.this, Transaction.class);
    			this.startActivity(nextScreen);
    			break;
    		}
    		case 6: {
    			List<Owned_Stock> stocks = s_db.getAllStocks();
    			XMLWriter writer = new XMLWriter(stocks);
    			Toast.makeText(getBaseContext(), "Exported successfully!",Toast.LENGTH_LONG).show();  
    			break;
    		}
    	}
    	return true;
    }
        
    //Login method
    private void login() {

    	AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this.getParent());
    	helpBuilder.setTitle("Login");
    	helpBuilder.setMessage("Enter Password: ");
    	final View layout = View.inflate(this, R.layout.login, null);
    	final EditText input = ((EditText) layout.findViewById(R.id.text));
    	helpBuilder.setIcon(0);
    	helpBuilder.setPositiveButton("OK",
	       	new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			String temp = input.getText().toString();
    			if (temp.equals(user.getPass())) {
    				Toast.makeText(getBaseContext(), "Login successfully",Toast.LENGTH_LONG).show();
    				user.setActive(true);	       	    		
    				int i = u_db.updateUser(user); 
	       	    	} else {
	       	    		Toast.makeText(getBaseContext(), "Invalid password!",Toast.LENGTH_LONG).show(); 
	       	    		login();
	       	    	}
	       	    }
    	 	});
	       	helpBuilder.setNegativeButton("Back",
	       			new DialogInterface.OnClickListener() {
	            	    public void onClick(DialogInterface dialog, int which) {
	            	    	Intent i = new Intent(getApplicationContext(), Main.class);
	            	    	finish();
	            	    	startActivity(i);
	            	    }
	       	});
	       	helpBuilder.setView(layout);
	       	 // Remember, create doesn't show the dialog
	       	AlertDialog helpDialog = helpBuilder.create();
	       	helpDialog.setCancelable(false);
	       	helpDialog.show();
    }
        
    private void register() {

          	 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
          	 helpBuilder.setTitle("Register");
          	 helpBuilder.setMessage("Enter new password and the money you have:");
          	 final View layout = View.inflate(this, R.layout.register, null);
          	 final EditText input1 = ((EditText) layout.findViewById(R.id.text1));
          	 final EditText input2 = ((EditText) layout.findViewById(R.id.text2));
          	 helpBuilder.setIcon(0);
          	 helpBuilder.setPositiveButton("OK",
          	   new DialogInterface.OnClickListener() {
          	    public void onClick(DialogInterface dialog, int which) {
          	    	String pass = input1.getText().toString();
          	    	String temp = input2.getText().toString();
          	    	//Float money = Float.parseFloat(temp);
          	    	if (temp.equals("")&(pass.equals(""))) {
          	    		Toast.makeText(getBaseContext(), "Please enter both info!",Toast.LENGTH_LONG).show();
          	    		register();
          	    	} else if (pass.equals("")) {
          	    		Toast.makeText(getBaseContext(), "Password cannot be blank!",Toast.LENGTH_LONG).show();
          	    		register();
          	    	} else if (temp.equals("")) {
          	    		Toast.makeText(getBaseContext(), "Enter the amount of money!",Toast.LENGTH_LONG).show();
          	    		register();
          	    	} else if (Float.parseFloat(temp)<=0) {
          	    		Toast.makeText(getBaseContext(), "The money must be bigger than zero!",Toast.LENGTH_LONG).show();
          	    		register();	
          	    	} else if (Float.parseFloat(temp)>0) {
          	    		//Add user info to DB
          	    		u_db.addUser(new User(pass,Float.parseFloat(temp),Float.parseFloat(temp),true));
          	    		user = u_db.getUser();
          	    		tv.setText("$"+Float.toString(user.getMoney()));
          	    		Toast.makeText(getBaseContext(), "Register successfully",Toast.LENGTH_LONG).show();
          	    	} else {
          	    		Toast.makeText(getBaseContext(), "Money must be a number!",Toast.LENGTH_LONG).show();
          	    		register();
          	    	}
          	    }
          	   });
          	 helpBuilder.setNegativeButton("Back",
             	   new DialogInterface.OnClickListener() {
             	    public void onClick(DialogInterface dialog, int which) {
             	    	Intent i = new Intent(getApplicationContext(), Main.class);
             	    	finish();
             	    	startActivity(i);
             	    }
        	 });
        	 helpBuilder.setView(layout);

          	 // Remember, create doesn't show the dialog
          	 AlertDialog helpDialog = helpBuilder.create();
          	 helpDialog.setCancelable(false);
          	 helpDialog.show();
        }
        
        //logout
    private void logout() {

          	 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
          	 helpBuilder.setTitle("Confirm");
          	 helpBuilder.setMessage("Are you sure?");
          	 helpBuilder.setPositiveButton("OK",
          	   new DialogInterface.OnClickListener() {
          	    public void onClick(DialogInterface dialog, int which) {
          	    	//Change active mode
          	    	user.setActive(false);
          	    	int i = u_db.updateUser(user);
          	    	//Back to login window
          	    	login();
          	    }
          	   });
          	helpBuilder.setNegativeButton("Cancel",
               new DialogInterface.OnClickListener() {
               	public void onClick(DialogInterface dialog, int which) {
               	    //Do nothing but close the window
               	 }
               	});
          	 
          	 // Remember, create doesn't show the dialog
          	 AlertDialog helpDialog = helpBuilder.create();
          	 helpDialog.setCancelable(false);
          	 helpDialog.show();
        }
        
    private void quickBuy(String name) {

        	 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(AccManager.this);
         	 helpBuilder.setTitle("Result");
         	 helpBuilder.setMessage("You haven't bought any \""+name+"\" yet. Would you like to buy some?");
         	 helpBuilder.setPositiveButton("OK",
         	   new DialogInterface.OnClickListener() {
         	    public void onClick(DialogInterface dialog, int which) {
         	    	//Get the number
         	    	number();
         	    }
         	 });
             helpBuilder.setNegativeButton("Cancel",
               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Do nothing but close the window
                }
           	 });    	 
             // Remember, create doesn't show the dialog
             AlertDialog helpDialog = helpBuilder.create();
             helpDialog.setCancelable(false);
             helpDialog.show();
       }
        
    public void number() {
    	
        	arrayAdapter = new OwnedListAdapter(this, R.layout.accitem, array);
        	AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        	helpBuilder.setTitle("Buy");
        	helpBuilder.setMessage("Enter the number and price:");
        	final View layout = View.inflate(this, R.layout.sell, null);
        	final EditText input1 = ((EditText) layout.findViewById(R.id.text1));
        	final EditText input2 = ((EditText) layout.findViewById(R.id.text2));
        	helpBuilder.setIcon(0);
        	helpBuilder.setPositiveButton("OK",
        		new DialogInterface.OnClickListener() {
        	    	public void onClick(DialogInterface dialog, int which) {
        	    		String text1 = input1.getText().toString();
        	    		String text2 = input2.getText().toString();
        	    		if (text2.equals("")&(text1.equals(""))) {
        	    			Toast.makeText(getBaseContext(), "Please enter both info!",Toast.LENGTH_LONG).show();
        	    			number();
        	    		} else if (text1.equals("")) {
        	    			Toast.makeText(getBaseContext(), "Please enter the number!",Toast.LENGTH_LONG).show();
        	    			number();
        	    		} else if (text2.equals("")) {
        	    			Toast.makeText(getBaseContext(), "Please enter the price!",Toast.LENGTH_LONG).show();
        	    			number();
        	    		} else if((isInteger(text1)==false)||(isFloat(text2)==false)) {
        	    			Toast.makeText(getBaseContext(), "Please enter numbers!",Toast.LENGTH_LONG).show();
        	    			number();
        	    		} else if (Float.parseFloat(text2)*Integer.parseInt(text1)>user.getMoney()) {
        	    			Toast.makeText(getBaseContext(), "Not enough money!",Toast.LENGTH_LONG).show();
        	    			number();	
        	    		} else if ((Integer.parseInt(text1)<=0)||(Float.parseFloat(text2)<=0)) {
        	    			Toast.makeText(getBaseContext(), "The number and the price must be bigger than 0!",Toast.LENGTH_LONG).show();
        	    			number();	
        	    		} else if(h_db.check(stock.getName())==0){
        	    			//First time buy
        	    			int number = Integer.parseInt(text1);
        	    			float p = Float.parseFloat(text2);
        	    			//Make change
        	    			user.setMoney(user.getMoney() - number*p);
        	    			u_db.updateUser(user);
        	    			stock.setNumber(number);
        	    			stock.setAvgBuyPrice(p);
        	    			s_db.addStock(stock);
        	    			array.add(0,stock); 
        	    			arrayAdapter.notifyDataSetChanged();
        	    			list.setAdapter(arrayAdapter);
          	    	
        	    			Date cDate = new Date();
	          	  			String time = new SimpleDateFormat("dd-MM-yyyy").format(cDate);
	          	  			History h = new History(time,stock.getName(),"buy",number,p);
	          	  			h_db.addHistory(h);
	          	  			tv.setText("$"+user.getMoney());
	          	  			Statistic stat = new Statistic(stock.getName(),0f,stock.getAvgBuyPrice(),stock.getAvgSellPrice());
	          	  			db.addStat(stat);
        	    		} else {
        	    			//Bought before
        	    			int number = Integer.parseInt(text1);
        	    			float p = Float.parseFloat(text2);
        	    			//Make change
        	    			stock.buy(number, p);
	          	  			tv.setText("$"+user.getMoney());
	          	  			s_db.addStock(stock);
	          	  			array.add(0,stock); 
	          	  			arrayAdapter.notifyDataSetChanged();
	          	  			list.setAdapter(arrayAdapter);
        	    		}
        	    	}
         		});
         	helpBuilder.setNegativeButton("Back",
          	   new DialogInterface.OnClickListener() {
          	    public void onClick(DialogInterface dialog, int which) {
          	    }
         	});
         	helpBuilder.setView(layout);
         	// Remember, create doesn't show the dialog
         	AlertDialog helpDialog = helpBuilder.create();
         	helpDialog.setCancelable(false);
         	helpDialog.show();         	
       }
    
    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {}
        return false;
    }
    
    public boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException nfe) {}
        return false;
    }
       
    public void updateNet() {

    	array = new ArrayList<Owned_Stock>();
    	Owned_Stock temp = new Owned_Stock();
    	List<Owned_Stock> stocks = s_db.getAllStocks(); 
    	for (Owned_Stock s: stocks) {
    		loadData(s.getName());
    		temp = new Owned_Stock(stock.getName(),stock.getCompany(),stock.getBuyPrice(),
    			stock.getChangeValue(),stock.getPercChange(),stock.getVolume(),stock.getTime(),
   	   	 		stock.getLow(),stock.getHigh(),stock.getPe(),stock.getMarketcap(),
   	   	 	    stock.getAvg_volume(),s.getNumber(),s.getAvgBuyPrice(),s.getAvgSellPrice(),s.getReview(),s.getBought());
    		array.add(0,temp);
    		s_db.updateStock(temp);
    	}
    	arrayAdapter = new OwnedListAdapter(this, R.layout.accitem, array);
    	arrayAdapter.notifyDataSetChanged();
    	list.setAdapter(arrayAdapter);
    }
}
