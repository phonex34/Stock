package com.example.stock;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StockDetail extends Activity {

	String stock_time,stock_change,stock_perchange,stock_BuyPrice, stock_name,stock_company,stock_volume,stock_high,stock_low,stock_marcap,stock_avgvolume;
	ImageView imageChart ;
	public void onCreate(Bundle savedInstanceState) throws NetworkOnMainThreadException
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_detail);
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
        stock_time=i.getStringExtra("stock_time");
        stock_marcap=i.getStringExtra("stock_marcap");
        stock_avgvolume=i.getStringExtra("stock_avgvolume");
    	final String URL = "https://www.google.com/finance/getchart?p=1d&i=240&q="+stock_name;
       
    	imageChart=(ImageView)findViewById(R.id.imageChart);
    	// Create an object for subclass of AsyncTask
    	ConnectionDetector cd;
		cd = new ConnectionDetector(StockDetail.this);
        if (cd.isConnectingToInternet()==false) {
            // Internet Connection is not present
        	
        	/*Log.e("loi netwod","fail");
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Network error");
			builder.setMessage("Could not connect to Internet!");
			builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub      
					
				}                        
	   	 	});
			builder.show();*/
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
      
        final TextView stockName = (TextView) findViewById(R.id.stock_name);
        stockName.setText(stock_name);
        final TextView stockBuyPrice = (TextView) findViewById(R.id.stock_Buyprice);
        stockBuyPrice.setText(stock_BuyPrice);
        final TextView stockChange = (TextView) findViewById(R.id.stock_change);
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
        	stockPerchange.setText(stock_perchange+"%");
        	stockPerchange.setTextColor(Color.YELLOW); 	
        }
        else if (tempPerchange >0)
        {
        	stockPerchange.setText("+"+stock_perchange+"%");
        	stockPerchange.setTextColor(Color.GREEN); 	
        }
        
        final TextView stockVolume= (TextView) findViewById(R.id.stock_volume);
        stockVolume.setText(stock_volume);
        final TextView stockHigh= (TextView) findViewById(R.id.stock_high);
        stockHigh.setText(stock_high);
        final TextView stockLow= (TextView) findViewById(R.id.stock_low);
        stockLow.setText(stock_low);
        final TextView stockTime= (TextView) findViewById(R.id.stock_time);
        stockTime.setText(stock_time);
        final TextView stockMarcap= (TextView) findViewById(R.id.stock_markcap);
        stockMarcap.setText(stock_marcap);
        final TextView stockAVGvolume= (TextView) findViewById(R.id.stock_avgvolume);
        stockAVGvolume.setText(stock_avgvolume);
        
        
        
        
       /* try {
        	 //final ImageView imageChart = (ImageView)findViewById(R.id.imageChart);
        	  String imageUrl="https://www.google.com/finance/getchart?p=1d&i=240&q="+stock_name;
        	  //Log.e("bi loi", imageUrl);
        	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
        	  imageChart.setImageBitmap(bitmap); 
        	} catch (MalformedURLException e) {
        	  e.printStackTrace();
        	} catch (IOException e) {
        	  e.printStackTrace();
        	}
        
        */
        final Button chart1 = (Button) findViewById(R.id.chart1d);
        OnClickListener chart1Listener = new OnClickListener() {       	
            public void onClick(View v) {
            	   try {
            	       	// final ImageView imageChart = (ImageView)findViewById(R.id.imageChart);
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
            		   		//final ImageView imageChart = (ImageView)findViewById(R.id.imageChart);
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
            	       //	 final ImageView imageChart = (ImageView)findViewById(R.id.imageChart);
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
}