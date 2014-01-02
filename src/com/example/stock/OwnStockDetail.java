package com.example.stock;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class OwnStockDetail extends Activity{
	String stock_change,stock_perchange,stock_BuyPrice, stock_name,stock_company,stock_volume,stock_high,stock_low,stock_number,stock_time,stock_marcap,stock_avgvolume,stock_avgbuy,stock_avgsell,stock_review;
	private OwnStockDBManager s_db = new OwnStockDBManager(this);
    private UserDBManager u_db = new UserDBManager(this);
    private HistoryDBManager h_db = new HistoryDBManager(this);
    private Owned_Stock stock = new Owned_Stock();
    private History h = new History();
    private User user = new User();
    private int number;
    private TextView stockName;
    private TextView stockBuyPrice;
    private TextView stockChange;
    private TextView stockNumber;
    private TextView stockAvgBuy;
    private TextView stockAvgSell;
    private TextView stockReview;
    TableLayout table;
    ImageView imageChart ;
    
	public void onCreate(Bundle savedInstanceState) throws NetworkOnMainThreadException
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ownstock_detail);
		Intent i = getIntent();
        stock_name = i.getStringExtra("stock_name");
        stock_company = i.getStringExtra("stock_company");
        stock_BuyPrice=i.getStringExtra("stock_BuyPrice");
        stock_change = i.getStringExtra("stock_change");
        float tempChange= Float.parseFloat(stock_change);
        stock_perchange = i.getStringExtra("stock_perchange");
        float tempPerchange=Float.parseFloat(stock_perchange);
        stock_volume=i.getStringExtra("stock_volume");
        stock_high = i.getStringExtra("stock_high");
        stock_low = i.getStringExtra("stock_low");
        stock_number = i.getStringExtra("stock_number");
        stock_time=i.getStringExtra("stock_time");
        stock_marcap=i.getStringExtra("stock_marcap");
        stock_avgvolume=i.getStringExtra("stock_avgvolume");
        stock_avgbuy=i.getStringExtra("stock_avgbuy");
        stock_avgsell=i.getStringExtra("stock_avgsell");
        stock_review=i.getStringExtra("stock_review");
        final String URL = "https://www.google.com/finance/getchart?p=1d&i=240&q="+stock_name;
        imageChart=(ImageView)findViewById(R.id.imageChart);
    	// Create an object for subclass of AsyncTask
    	ConnectionDetector cd;
		cd = new ConnectionDetector(OwnStockDetail.this);
        if (cd.isConnectingToInternet()==false) {
            // Internet Connection is not present
			Toast.makeText(getBaseContext(), "Connect to Internet to get charts!",Toast.LENGTH_LONG).show();
        }
        else {
    	
    	GetXMLTask task = new GetXMLTask();
        // Execute the task
        task.execute(new String[] { URL });
        }
        
        //set detail
        setTitle(stock_company);
        setTitleColor(Color.parseColor("#FF9900"));
        stockName = (TextView) findViewById(R.id.stock_name);
        stockBuyPrice = (TextView) findViewById(R.id.stock_Buyprice);
        stockChange = (TextView) findViewById(R.id.stock_change);
        stockName.setText(stock_name);
        
        stockBuyPrice.setText(stock_BuyPrice);
        
        if(tempChange<0)
        {
        	   stockChange.setText(stock_change);
        	   stockChange.setTextColor(Color.RED);
        }
        else if (tempChange ==0)
        {
        	stockChange.setText(stock_change);
        	stockChange.setTextColor(Color.YELLOW); 	
        }
        else if (tempChange >0)
        {
        	stockChange.setText("+"+stock_change);
        	stockChange.setTextColor(Color.GREEN); 	
        }
        final TextView stockPerchange = (TextView) findViewById(R.id.stock_Perc_change);
        if(tempPerchange<0)
        {
        	   stockPerchange.setText(stock_perchange+"%");
        	   stockPerchange.setTextColor(Color.RED);
        }
        else if (tempPerchange ==0)
        {
        	stockPerchange.setText(stock_perchange);
        	stockPerchange.setTextColor(Color.YELLOW); 	
        }
        else if (tempPerchange >0)
        {
        	stockPerchange.setText("+"+stock_perchange+"%");
        	stockPerchange.setTextColor(Color.GREEN); 	
        }
        
        table = (TableLayout) findViewById(R.id.table);
        final TextView stockVolume= (TextView) findViewById(R.id.stock_volume);
        stockVolume.setText(stock_volume);
        final TextView stockHigh= (TextView) findViewById(R.id.stock_high);
        stockHigh.setText(stock_high);
        final TextView stockLow= (TextView) findViewById(R.id.stock_low);
        stockLow.setText(stock_low);
        final TextView stockTime= (TextView) findViewById(R.id.stock_time);
        stockTime.setText(stock_time);
        final TextView stockAvgVolume= (TextView) findViewById(R.id.stock_avgvolume);
        stockAvgVolume.setText(stock_avgvolume);
        final TextView stockMarkCap= (TextView) findViewById(R.id.stock_markcap);
        stockMarkCap.setText(stock_marcap);
        stockAvgBuy= (TextView) findViewById(R.id.stock_avgbuy);
        stockAvgBuy.setText(stock_avgbuy);
        stockAvgSell= (TextView) findViewById(R.id.stock_avgsell);
        stockAvgSell.setText(stock_avgsell);
        stockNumber= (TextView) findViewById(R.id.stock_number);
        stockNumber.setText(stock_number);
        stockReview= (TextView) findViewById(R.id.stock_statistic);
        stockReview.setText(stock_review + "%");
        stockReview.setTypeface(null, Typeface.BOLD);
        
        final Button buy = (Button) findViewById(R.id.buy);
        OnClickListener buyListener = new OnClickListener() {       	
            public void onClick(View v) {
                stock = s_db.getStock(stock_name);
                b_number();
            }
        };
        buy.setOnClickListener(buyListener); 
        
        final Button sell = (Button) findViewById(R.id.sell);
        OnClickListener sellListener = new OnClickListener() {       	
            public void onClick(View v) {
            	stock = s_db.getStock(stock_name);
                s_number();
            }
        };
        sell.setOnClickListener(sellListener); 
        
        
        /*try {
	       	 final ImageView imageChart = (ImageView)findViewById(R.id.imageChart);
	       	  String imageUrl="https://www.google.com/finance/getchart?p=1d&i=240&q="+stock_name;
	       	  
	       	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
	       	  imageChart.setImageBitmap(bitmap); 
	       	} catch (MalformedURLException e) {
	       	  e.printStackTrace();
	       	} catch (IOException e) {
	       	  e.printStackTrace();
	       	}*/
        
        
        final Button chart1 = (Button) findViewById(R.id.chart1d);
        OnClickListener chart1Listener = new OnClickListener() {       	
            public void onClick(View v) {
            	   try {
            	       	 final ImageView imageChart = (ImageView)findViewById(R.id.imageChart);
            	       	  String imageUrl="https://www.google.com/finance/getchart?p=1d&i=240&q="+stock_name;
            	       	  //Log.e("bi loi", imageUrl);
            	       	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
            	       	  imageChart.setImageBitmap(bitmap); 
            	       	} catch (MalformedURLException e) {
            	       	  e.printStackTrace();
            	       	} catch (IOException e) {
            	       	  e.printStackTrace();
            	       	}
            		
            }
        };
        chart1.setOnClickListener(chart1Listener);  
        
        
        final Button chart2 = (Button) findViewById(R.id.chart1m);
        OnClickListener chart2Listener = new OnClickListener() {       	
            public void onClick(View v) {
            	   try {
            	       	 final ImageView imageChart = (ImageView)findViewById(R.id.imageChart);
            	       	  String imageUrl="https://www.google.com/finance/getchart?p=1M&i=240&q="+stock_name;
            	       	  //Log.e("bi loi", imageUrl);
            	       	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
            	       	  imageChart.setImageBitmap(bitmap); 
            	       	} catch (MalformedURLException e) {
            	       	  e.printStackTrace();
            	       	} catch (IOException e) {
            	       	  e.printStackTrace();
            	       	}
            		
            }
        };
        
        chart2.setOnClickListener(chart2Listener); 
        
        
        final Button chart3 = (Button) findViewById(R.id.chart1y);
        OnClickListener chart3Listener = new OnClickListener() {       	
            public void onClick(View v) {
            	   try {
            	       	 final ImageView imageChart = (ImageView)findViewById(R.id.imageChart);
            	       	  String imageUrl="https://www.google.com/finance/getchart?p=1Y&i=240&q="+stock_name;
            	       	  //Log.e("bi loi", imageUrl);
            	       	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
            	       	  imageChart.setImageBitmap(bitmap); 
            	       	} catch (MalformedURLException e) {
            	       	  e.printStackTrace();
            	       	} catch (IOException e) {
            	       	  e.printStackTrace();
            	       	}
            		
            }
        };
        chart3.setOnClickListener(chart3Listener); 
        
	    TableRow tr_head = new TableRow(this);
	    tr_head.setId(10);
	    tr_head.setBackgroundColor(Color.GRAY);
	    tr_head.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	    
	    TextView label_date = new TextView(this);
        label_date.setId(20);
        label_date.setText("Date");
        label_date.setTypeface(null, Typeface.BOLD);
        label_date.setPadding(40, 5, 15, 0);
        tr_head.addView(label_date);// add the column to the table row here
        
        TextView type= new TextView(this);
        type.setId(22);
        type.setText("Type");
        type.setTypeface(null, Typeface.BOLD);
        type.setPadding(0, 5, 5, 10);
        tr_head.addView(type);// add the column to the table row here
                
        TextView number= new TextView(this);
        number.setId(23);
        number.setText("Number");
        number.setTypeface(null, Typeface.BOLD);
        number.setPadding(0, 5, 15, 5);
        tr_head.addView(number);// add the column to the table row here
                
        TextView price= new TextView(this);
        price.setId(24);
        price.setText("Price");
        price.setTypeface(null, Typeface.BOLD);
        price.setPadding(5, 0, 10, 5);
        tr_head.addView(price);// add the column to the table row here
        
        table.addView(tr_head, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        
        List<History>  list = h_db.getMiniHistory(stock_name); 
	    ListIterator<History> li = list.listIterator(list.size());
	    List<History> List = new ArrayList<History>();
	    // Iterate in reverse.
	    while(li.hasPrevious()) {
	    	List.add(li.previous());
	    }	    
	    for (History temp : List) {
	    	TableRow tr = new TableRow(this);
	    	tr.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	    	TextView labelDATE = new TextView(this);
	    	labelDATE.setText(temp.getTime());
	    	labelDATE.setPadding(10, 5, 5, 5);
	    	labelDATE.setTextColor(Color.WHITE);
	    	tr.addView(labelDATE);
	    	TextView TYPE = new TextView(this);
	    	TYPE.setText(temp.getType());
	    	TYPE.setTextColor(Color.WHITE);
	    	tr.addView(TYPE);
	    	TextView NUMBER = new TextView(this);
	    	NUMBER.setText(Integer.toString(temp.getNumber()));
	    	NUMBER.setPadding(10, 5, 65, 5);
	    	NUMBER.setTextColor(Color.WHITE);
	    	tr.addView(NUMBER);
	    	TextView PRICE = new TextView(this);
	    	PRICE.setText(Float.toString(temp.getPrice()));
	    	PRICE.setPadding(5, 0, 5, 5);
	    	PRICE.setTextColor(Color.WHITE);
	    	tr.addView(PRICE);
	    	table.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        }
	}
    
    public void b_number() {
 	  
    	user = u_db.getUser();
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
    	    		b_number();
    	    	} else if (text1.equals("")) {
    	    		Toast.makeText(getBaseContext(), "Please enter the number!",Toast.LENGTH_LONG).show();
    	    		b_number();
    	    	} else if (text2.equals("")) {
    	    		Toast.makeText(getBaseContext(), "Please enter the price!",Toast.LENGTH_LONG).show();
    	    		b_number();
    	    	} else if((isInteger(text1)==false)||(isFloat(text2)==false)) {
    	    		Toast.makeText(getBaseContext(), "Please enter numbers!",Toast.LENGTH_LONG).show();
    	    		b_number();
    	    	} else if (Integer.parseInt(text1)*Float.parseFloat(text2)>user.getMoney()) {
    	    		Toast.makeText(getBaseContext(), "Not enough money!",Toast.LENGTH_LONG).show();
    	    		b_number();	
    	    	} else if ((Integer.parseInt(text1)<=0)||(Float.parseFloat(text2)<=0)) {
    	    		Toast.makeText(getBaseContext(), "The number and the price must be bigger than 0!",Toast.LENGTH_LONG).show();
    	    		b_number();	
    	    	} else {
    	    		number = Integer.parseInt(text1);
    	    		float price = Float.parseFloat(text2);
      	    		//Make change      	    		
      	    		Log.e("buy",Float.toString(price));
      	    		stock.buy(number,price);
      	    		Toast.makeText(getBaseContext(), "Purchase successfully!",Toast.LENGTH_LONG).show();
      	    		if (s_db.check(stock.getName())==1) {
      	    			s_db.updateStock(stock);
      	    			Log.e("old",stock.getName());
      	    		}
      	    		else 
      	    			{
      	    				s_db.addStock(stock);
      	    				Log.e("new",stock.getName());
      	    			}
      	    		//reload miniHistory    	    		
      	    		stockNumber.setText(Integer.toString(stock.getNumber()));
      	    		stockAvgBuy.setText(Float.toString(stock.getAvgBuyPrice()));
      	    		stockReview.setText(String.format("%.2f",stock.getReview())+"%");
      	    		h = h_db.getLastHistory();
      	    		updateHistory(h);
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
    
    private void s_number() 
    {
     	 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
     	 helpBuilder.setTitle("Sell");
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
     	    		s_number();
     	    	} else if (text1.equals("")) {
     	    		Toast.makeText(getBaseContext(), "Please enter the number!",Toast.LENGTH_LONG).show();
     	    		s_number();
     	    	} else if (text2.equals("")) {
     	    		Toast.makeText(getBaseContext(), "Please enter the price!",Toast.LENGTH_LONG).show();
     	    		s_number();
     	    	} else if((isInteger(text1)==false)||(isFloat(text2)==false)) {
     	    		Toast.makeText(getBaseContext(), "Please enter numbers!",Toast.LENGTH_LONG).show();
     	    		s_number();
     	    	} else if (Integer.parseInt(text1) > stock.getNumber()) {
     	    		Toast.makeText(getBaseContext(), "Not enough stock!",Toast.LENGTH_LONG).show();
     	    		s_number();	
     	    	} else if ((Integer.parseInt(text1)<=0)||(Float.parseFloat(text2)<=0)) {
     	    		Toast.makeText(getBaseContext(), "The number and the price must be bigger than 0!",Toast.LENGTH_LONG).show();
     	    		s_number();	
     	    	} else {
     	    		number = Integer.parseInt(text1);
     	    		float price = Float.parseFloat(text2);
      	    		//Make change      	    		
      	    		stock.sell(number,price);
      	    		//Check if sold out
      	    		
      	    		Toast.makeText(getBaseContext(), "Purchase successfully!",Toast.LENGTH_LONG).show();     	    		
      	    		//reload
      	    		stockNumber.setText(Integer.toString(stock.getNumber()));
      	    		stockAvgSell.setText(Float.toString(stock.getAvgSellPrice()));
      	    		stockReview.setText(String.format("%.2f",stock.getReview())+"%");
      	    		h = h_db.getLastHistory();
      	    		updateHistory(h);
      	    		if(stock.getNumber()==0) {
      	    			//If so delete from DB
      	    			s_db.deleteStock(stock);
      	    			AlertDialog.Builder builder = new AlertDialog.Builder(OwnStockDetail.this);
      	    			builder.setTitle("Info");
      	    			builder.setMessage("This portfolio has been sold out!");
      	    			builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
      	  				public void onClick(DialogInterface dialog, int which) {
      	  					// TODO Auto-generated method stub      
      	  					back();
      	  				}                        
      	  	   	 	});
      	  			builder.show();
      	    		} else s_db.updateStock(stock);
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
    
   public void back() {
	   
	   super.onBackPressed();
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
    
    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                Log.e("loi load image",url);
            	map = downloadImage(url);
                
            }
            return map;
        }
        
        
        
        protected void onPostExecute(Bitmap result) {
            imageChart.setImageBitmap(result);
        }
 
        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
 
            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }
 
        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
 
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
 
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }     
    }
    
    public void updateHistory(History h) {
    	
    	TableRow tr = new TableRow(this);
    	tr.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
    	TextView labelDATE = new TextView(this);
    	labelDATE.setText(h.getTime());
    	labelDATE.setPadding(10, 5, 5, 5);
    	labelDATE.setTextColor(Color.WHITE);
    	tr.addView(labelDATE);
    	TextView TYPE = new TextView(this);
    	TYPE.setText(h.getType());
    	TYPE.setTextColor(Color.WHITE);
    	tr.addView(TYPE);
    	TextView NUMBER = new TextView(this);
    	NUMBER.setText(Integer.toString(h.getNumber()));
    	NUMBER.setPadding(10, 5, 65, 5);
    	NUMBER.setTextColor(Color.WHITE);
    	tr.addView(NUMBER);
    	TextView PRICE = new TextView(this);
    	PRICE.setText(Float.toString(h.getPrice()));
    	PRICE.setPadding(5, 0, 5, 5);
    	PRICE.setTextColor(Color.WHITE);
    	tr.addView(PRICE);
    	table.addView(tr, 1);
    }
}

