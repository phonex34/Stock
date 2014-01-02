package com.example.stock;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StockDBManager extends SQLiteOpenHelper {
	
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    public StockDBManager(Context context) {
        super(context,"StockTable", null, DATABASE_VERSION);
    }
    
 // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = 
        		"CREATE TABLE StockTable (" +
                	"name TEXT PRIMARY KEY," +
                    "company TEXT," +
                 	"buy_price REAL," +
        			"change_value REAL," +
        			"perc_change REAL," +
        			"volume REAL," +
        			"time TEXT," +
        			"low REAL," +
        			"high REAL," +
        			"pe REAL,"+
        			"market_cap REAL,"+
        			"avg_volume REAL)";
        db.execSQL(create_query);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS StockTable");
 
        // Create tables again
        onCreate(db);
    }
    
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new stock
    void addStock(Stock stock) {
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
        values.put("market_cap",stock.getMarketcap());
        values.put("avg_volume", stock.getAvg_volume());
        // Inserting Row
        db.insert("StockTable", null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single contact
    Stock getStock(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Stock stock = new Stock("","",0f,0f,0f,0f,"",0f,0f,0f,0f,0f);
        Cursor cursor = db.query("StockTable", new String[] { "name","company",
                "buy_price","change_value","perc_change","volume","time","low","high","pe","market_cap","avg_volume"}, "name" + "=?",
                new String[] { name }, null, null, null, null);
        if ( cursor.moveToFirst()) {
 
            stock = new Stock(cursor.getString(0),cursor.getString(1), 
        		Float.parseFloat(cursor.getString(2)),Float.parseFloat(cursor.getString(3)),
        		Float.parseFloat(cursor.getString(4)),Float.parseFloat(cursor.getString(5)),
        		cursor.getString(6),Float.parseFloat(cursor.getString(7)),Float.parseFloat(cursor.getString(8)),
        		Float.parseFloat(cursor.getString(9)),Float.parseFloat(cursor.getString(10)),Float.parseFloat(cursor.getString(11)));
        } 
        return stock;
    }
 
    // Getting All Contacts
    public List<Stock> getAllStocks() {
        List<Stock> List = new ArrayList<Stock>();
        // Select All Query
        String selectQuery = "SELECT * FROM StockTable";
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Stock stock = new Stock();
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
                // Adding contact to list
                List.add(stock);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return List;
    }
 
    // Updating single contact
    public int updateStock(Stock stock) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put("company", stock.getCompany()); 
        values.put("buy_price", stock.getBuyPrice());
        values.put("change_value", stock.getChangeValue());
        values.put("time", stock.getTime());
 
        // updating row
        return db.update("StockTable", values, "name = ?",
                new String[] { stock.getName() });
    }
 
    // Deleting single contact
    public void deleteStock(Stock stock) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("StockTable", "name = ?",
                new String[] { stock.getName() });
        db.close();
    }
    
    // Deleting all item
    public void deleteAllStock() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete("StockTable",null,null);
    	db.close();
    }
 
    // Getting contacts Count
    public int getStocksCount() {
        String countQuery = "SELECT  * FROM StockTable";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();
 
        // return count
        if ( cursor.moveToFirst())
        return cursor.getCount();
        else return 0;
    }
}
