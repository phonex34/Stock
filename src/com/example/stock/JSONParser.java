package com.example.stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;
    static JSONArray jArray = null;
    static JSONObject jo = null;
    String json = "";
 
    // constructor
    public JSONParser() {
 
    }
 
    public JSONArray getJSONFromUrl(String url) {
    	
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url);
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
               sb.append(line + "\n");
            }
            is.close();
            //json = sb.toString().substring(4);
            json = sb.toString();
            if (json.equals("")) return null;
            sb.delete(0, 4);
    		sb.deleteCharAt(sb.length() - 1);
    		json = sb.toString();
            //Toast.makeText(AccManager.appContext,json,Toast.LENGTH_LONG).show();
            Log.e("Buffer", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        
        
 
        try {
        	jArray = new JSONArray(json);
        }
        catch (Exception e) {
        	Log.e("Create Error", "Error when create obj " + e.toString());
        }
        // return JSON String
        return jArray;
    }
}
