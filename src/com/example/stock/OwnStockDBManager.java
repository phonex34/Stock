package com.example.stock;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class OwnStockDBManager extends SQLiteOpenHelper {
	
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    public OwnStockDBManager(Context context) {
        super(context,"OwnStockTable", null, DATABASE_VERSION);
    }
    
 // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = 
        		"CREATE TABLE OwnStockTable (" +
                	"name TEXT PRIMARY KEY," +
                    "company TEXT," +
                 	"buy_price REAL," +
        			"change_value REAL," +
        			"perc_change REAL," +
        			"volume REAL," +
        			"time TEXT," +
        			"low REAL," +
        			"high REAL," +
        			"pe REAL," +
        			"market_cap REAL,"+
        			"avg_volume REAL,"+
        			"number INTEGER," +
        			"avg_buy_price REAL," +
        			"avg_sell_price REAL," +
        			"review REAL," +
        			"bought INTEGER )";
        			
        db.execSQL(create_query);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS OwnStockTable");
 
        // Create tables again
        onCreate(db);
    }
    
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new stock
    void addStock(Owned_Stock stock) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put("name", stock.getName());
        values.put("company", stock.getCompany()); 
        values.put("buy_price", stock.getBuyPrice());
        values.put("change_value", stock.getChangeValue());
        values.put("perc_change", stock.getPercChange());
        values.put("volume", stock.getVolume());
        values.put("time", stock.getTime());       
        values.put("low", stock.getLow());
        values.put("high", stock.getHigh());
        values.put("pe", stock.getPe());
        values.put("market_cap", stock.getMarketcap());
        values.put("avg_volume",stock.getAvg_volume());
        values.put("number", stock.getNumber());
        values.put("avg_buy_price", stock.getAvgBuyPrice());
        values.put("avg_sell_price", stock.getAvgSellPrice());
        values.put("review", stock.getReview());
        values.put("bought", stock.getBought());

        // Inserting Row
        db.insert("OwnStockTable", null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single contact
    Owned_Stock getStock(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Owned_Stock stock = new Owned_Stock("","",0f,0f,0f,0f,"",0f,0f,0f,0f,0f,0,0f,0f,0f,0);
        Cursor cursor = db.query("OwnStockTable", new String[] { "name","company",
                "buy_price","change_value","perc_change","volume","time","low","high","pe",
                "market_cap","avg_volume","number","avg_buy_price","avg_sell_price","review","bought" }, "name = ?",
                new String[] { name }, null, null, null, null);
        if ( cursor.moveToFirst()) {
            stock = new Owned_Stock(cursor.getString(0),cursor.getString(1), 
        		Float.parseFloat(cursor.getString(2)),Float.parseFloat(cursor.getString(3)),
        		Float.parseFloat(cursor.getString(4)),Float.parseFloat(cursor.getString(5)),
        		cursor.getString(6),Float.parseFloat(cursor.getString(7)),
        		Float.parseFloat(cursor.getString(8)),Float.parseFloat(cursor.getString(9)),
        		Float.parseFloat(cursor.getString(10)),Float.parseFloat(cursor.getString(11)),
        		Integer.parseInt(cursor.getString(12)),
        		Float.parseFloat(cursor.getString(13)),Float.parseFloat(cursor.getString(14)),
        		Float.parseFloat(cursor.getString(15)),Integer.parseInt(cursor.getString(16)));
        } 
        return stock;
    }
    
    public int check(String name) {
    	
    	int result=0;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("OwnStockTable", new String[] { "name","company",
                "buy_price","change_value","perc_change","volume","time","low","high","pe",
                "market_cap","avg_volume","number","avg_buy_price","avg_sell_price","review","bought" }, "name = ?",
                new String[] { name }, null, null, null, null);
    	if (cursor.moveToFirst()) {
            result=cursor.getCount();
        }
    	return result;
    }
 
    // Getting All Contacts
    public List<Owned_Stock> getAllStocks() {
        List<Owned_Stock> List = new ArrayList<Owned_Stock>();
        // Select All Query
        String selectQuery = "SELECT * FROM OwnStockTable";
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Owned_Stock stock = new Owned_Stock();
                stock.setName(cursor.getString(0));
                stock.setCompany(cursor.getString(1));
                stock.setBuyPrice(Float.parseFloat(cursor.getString(2)));
                stock.setChangeValue(Float.parseFloat(cursor.getString(3)));
                stock.setPercChange(Float.parseFloat(cursor.getString(4)));
                stock.setVolume(Float.parseFloat(cursor.getString(5)));
                stock.setTime(cursor.getString(6));                
               
                stock.setLow(Float.parseFloat(cursor.getString(7)));
                stock.setHigh(Float.parseFloat(cursor.getString(8)));
                stock.setPe(Float.parseFloat(cursor.getString(9)));
                stock.setMarketcap(Float.parseFloat(cursor.getString(10)));
                stock.setAvg_volume(Float.parseFloat(cursor.getString(11)));
                stock.setNumber(Integer.parseInt(cursor.getString(12)));
                stock.setAvgBuyPrice(Float.parseFloat(cursor.getString(13)));
                stock.setAvgSellPrice(Float.parseFloat(cursor.getString(14)));
                stock.setReview(Float.parseFloat(cursor.getString(15)));
                stock.setBought(Integer.parseInt(cursor.getString(16)));
                
                // Adding contact to list
                List.add(stock);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return List;
    }
 
    // Updating single contact
    public int updateStock(Owned_Stock stock) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put("company", stock.getCompany()); 
        values.put("buy_price", stock.getBuyPrice());
        values.put("change_value", stock.getChangeValue());
        values.put("time", stock.getTime());        
        values.put("high", stock.getHigh());
        values.put("low", stock.getLow());
        values.put("pe", stock.getPe());
        values.put("market_cap", stock.getMarketcap());
        values.put("avg_volume", stock.getAvg_volume());
        values.put("number", stock.getNumber());
        values.put("avg_buy_price", stock.getAvgBuyPrice());
        values.put("avg_sell_price", stock.getAvgSellPrice());
        values.put("review", stock.getReview());
        values.put("bought", stock.getBought());

        // updating row
        return db.update("OwnStockTable", values, "name = ?",
                new String[] { stock.getName() });
    }
    
    // Saving to file
    public void save() {
    	List<Owned_Stock> List = new ArrayList<Owned_Stock>();
        // Select All Query
        String selectQuery = "SELECT * FROM OwnStockTable";
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            File myFile = new File("/sdcard/mysdfile.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = 
                                    new OutputStreamWriter(fOut);
            if (cursor.moveToFirst()) {
                do {
                	String temp = cursor.getString(0) + cursor.getString(1);
                	myOutWriter.append(temp);
                } while (cursor.moveToNext());
            }
            myOutWriter.close();
            fOut.close();
            Toast.makeText(AccManager.appContext,"Done writing SD 'mysdfile.txt'",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(AccManager.appContext,"Fail to save data! ",Toast.LENGTH_SHORT).show();
        }
    }
 
    // Deleting single contact
    public void deleteStock(Owned_Stock stock) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("OwnStockTable", "name = ?",
                new String[] { stock.getName() });
        db.close();
    }
    
    // Deleting all item
    public void deleteAllStock() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete("OwnStockTable",null,null);
    	db.close();
    }
 
    // Getting contacts Count
    public int getStocksCount() {
        String countQuery = "SELECT  * FROM OwnStockTable";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();
 
        // return count
        if ( cursor.moveToFirst())
        return cursor.getCount();
        else return 0;
    }
}
