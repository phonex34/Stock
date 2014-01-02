package com.example.stock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class UserDBManager extends SQLiteOpenHelper {

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    public UserDBManager(Context context) {
        super(context,"UserTable", null, DATABASE_VERSION);
    }
    
 // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = 
        		"CREATE TABLE UserTable (" +
        			"id INTEGER ," +
                	"pass TEXT PRIMARY KEY," +
                 	"money REAL," +
                 	"first REAL," +
        			"active INTEGER )";
        db.execSQL(create_query);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS UserTable");
 
        // Create tables again
        onCreate(db);
    }
    
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new User
    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i=1;
        ContentValues values = new ContentValues();
        values.put("id", i);
        values.put("pass", user.getPass());
        values.put("money", user.getMoney()); 
        values.put("first", user.getMoney());
        values.put("active", user.getActive());
 
        // Inserting Row
        db.insert("UserTable", null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single contact
    User getUser() {
    	
    	SQLiteDatabase db = this.getReadableDatabase();  
    	User user = new User();
        //Cursor cursor = db.query("UserTable", new String[] { "id","pass", "money","active" }, "id=?",
                //new String[] {"1"}, null, null, null, null);
    	String sql = "SELECT * FROM UserTable WHERE id = 1";
    	Cursor cursor = db.rawQuery(sql,null);
        if ( cursor.moveToFirst()) {
 
            user = new User(cursor.getString(1),Float.parseFloat(cursor.getString(2)), 
            		Float.parseFloat(cursor.getString(3)),Boolean.parseBoolean(cursor.getString(4)));
        } else user = new User("",-1f,-1f,false);
        return user;
    }
 
    // Updating single contact
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put("pass", user.getPass());
        values.put("money", user.getMoney()); 
        values.put("first", user.getFirst());
        values.put("active", user.getActive());
 
        // updating row
        return db.update("UserTable", values, "id = 1",
                null);
    }
    
    // Deleting all item
    public void deleteAllUser() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete("UserTable",null,null);
    	db.close();
    }
 
    // Getting contacts Count
    public int getUsersCount() {
        String countQuery = "SELECT  * FROM UserTable";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();
 
        // return count
        if ( cursor.moveToFirst())
        return cursor.getCount();
        else return 0;
    }
}