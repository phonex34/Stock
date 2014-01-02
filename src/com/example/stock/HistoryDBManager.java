package com.example.stock;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryDBManager extends SQLiteOpenHelper {
	
	// Database Version
    private static final int DATABASE_VERSION = 1;
 
    public HistoryDBManager(Context context) {
        super(context,"HistoryTable", null, DATABASE_VERSION);
    }
    
 // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = 
        		"CREATE TABLE HistoryTable (" +
        			"time TEXT," +
        			"name TEXT," +
        			"type TEXT," +
        			"number INTEGER," +
        			"price REAL )";
        db.execSQL(create_query);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS HistoryTable");
        // Create tables again
        onCreate(db);
    }
    
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new stock
    void addHistory(History h) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time", h.getTime());
        values.put("name", h.getName()); 
        values.put("type", h.getType());
        values.put("number", h.getNumber());
        values.put("price", h.getPrice()); 
 
        // Inserting Row
        db.insert("HistoryTable", null, values);
        db.close(); // Closing database connection
    }
 
    // Getting All Contacts
    public List<History> getMiniHistory(String name) {
    	
        List<History> List = new ArrayList<History>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("HistoryTable", new String[] { "time", "name", "type", "number", "price"}, "name" + "=?",
                new String[] { name }, null, null, null, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History h = new History();
                h.setTime(cursor.getString(0));
                h.setName(cursor.getString(1));
                h.setType(cursor.getString(2));
                h.setNumber(Integer.parseInt(cursor.getString(3)));
                h.setPrice(Float.parseFloat(cursor.getString(4)));
                // Adding contact to list
                List.add(h);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return List;
    }
    
    public History getLastHistory() {
        // Select All Query
        String selectQuery = "SELECT * FROM HistoryTable";
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        cursor.moveToLast();
        History h = new History();
        h.setTime(cursor.getString(0));
        h.setName(cursor.getString(1));
        h.setType(cursor.getString(2));
        h.setNumber(Integer.parseInt(cursor.getString(3)));
        h.setPrice(Float.parseFloat(cursor.getString(4)));
        // return contact list
        return h;
    }
    
    public Float calculate(String name,String type) {
    	
    	Float sum=0f;
    	int i = 0;
    	Float result=-1f;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("HistoryTable", new String[] { "time","name","type","number","price" }, 
    			"name = ? AND type = ?",new String[] { name,type }, null, null, null, null);
    	if (cursor.moveToFirst()) {
            do {
            	sum=sum + Integer.parseInt(cursor.getString(3))*Float.parseFloat(cursor.getString(4));
                i=i+Integer.parseInt(cursor.getString(3));
            } while (cursor.moveToNext());
            result = sum/i;
        }
    	return result;
    }
    
    public int check(String name) {
    	
    	int result=0;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("HistoryTable", new String[] { "time","name","type","number","price" }, 
    			"name = ?",new String[] { name }, null, null, null, null);
    	if (cursor.moveToFirst()) {
            result=cursor.getCount();
        }
    	return result;
    }
    
    // Getting All Contacts
    public List<History> getAllHistory() {
        List<History> List = new ArrayList<History>();
        // Select All Query
        String selectQuery = "SELECT * FROM HistoryTable";
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History h = new History();
                h.setTime(cursor.getString(0));
                h.setName(cursor.getString(1));
                h.setType(cursor.getString(2));
                h.setNumber(Integer.parseInt(cursor.getString(3)));
                h.setPrice(Float.parseFloat(cursor.getString(4)));
                // Adding contact to list
                List.add(h);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return List;
    }
    
    // Deleting all item
    public void deleteAllHistory() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete("HistoryTable",null,null);
    	db.close();
    }
 
    // Getting contacts Count
    public int getHistoryCount() {
        String countQuery = "SELECT  * FROM HistoryTable";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();
 
        // return count
        if ( cursor.moveToFirst())
        return cursor.getCount();
        else return 0;
    }
}