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

public class StatDBManager extends SQLiteOpenHelper {
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    public StatDBManager(Context context) {
        super(context,"StatTable", null, DATABASE_VERSION);
    }
    
 // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = 
        		"CREATE TABLE StatTable (" +
                	"name TEXT PRIMARY KEY," +
                 	"delta REAL," +
        			"AvgBuy REAL," +
                 	"AvgSell REAL )";
        			
        db.execSQL(create_query);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS StatTable");
 
        // Create tables again
        onCreate(db);
    }
    
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    
    void addStat(Statistic stat) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put("name", stat.getName());
        values.put("delta", stat.getDelta()); 
        values.put("AvgBuy", stat.getAvgBuy());
        values.put("AvgSell", stat.getAvgSell());

        // Inserting Row
        db.insert("StatTable", null, values);
        db.close(); // Closing database connection
    }
 
    // Getting All Contacts
    public List<Statistic> getAllStats() {
        List<Statistic> List = new ArrayList<Statistic>();
        // Select All Query
        String selectQuery = "SELECT * FROM StatTable";
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Statistic stat = new Statistic();
                stat.setName(cursor.getString(0));
                stat.setDelta(Float.parseFloat(cursor.getString(1)));
                stat.setAvgBuy(Float.parseFloat(cursor.getString(2)));
                stat.setAvgSell(Float.parseFloat(cursor.getString(3)));
                
                // Adding contact to list
                List.add(stat);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return List;
    }
 
    // Updating single contact
    public int updateStat(Statistic stat) {
    	
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put("name", stat.getName());
        values.put("delta", stat.getDelta()); 
        values.put("AvgBuy", stat.getAvgBuy());
        values.put("AvgSell", stat.getAvgSell());

        // updating row
        return db.update("StatTable", values, "name = ?",
                new String[] { stat.getName() });
    }
    
    public int check(String name) {
    	
    	int result=0;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("StatTable", new String[] { "name","delta","avgbuy","avgsell" }, 
    			"name = ?",new String[] { name }, null, null, null, null);
    	if (cursor.moveToFirst()) {
            result=cursor.getCount();
        }
    	return result;
    }
    
    // Deleting all item
    public void deleteAllStat() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete("StatTable",null,null);
    	db.close();
    }
 
    // Getting contacts Count
    public int getStatsCount() {
        String countQuery = "SELECT  * FROM StatTable";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();
 
        // return count
        if ( cursor.moveToFirst())
        return cursor.getCount();
        else return 0;
    }
}

