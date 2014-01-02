package com.example.stock;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserControl extends Activity {
	
    private OwnStockDBManager s_db = new OwnStockDBManager(this);
    private UserDBManager u_db = new UserDBManager(this);
    private HistoryDBManager h_db = new HistoryDBManager(this);
    private StatDBManager db = new StatDBManager(this);
    private User user = new User();
    private TextView row1,row2;
    private TextView tv1,tv2;
    private Button withdraw;
    private Button deposit;
    private Button change;
    private Button reset;
    private Float a;

	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.manage);
        
        user = u_db.getUser();
        withdraw = (Button) findViewById(R.id.withdraw);
        deposit = (Button) findViewById(R.id.Deposit);
        change = (Button) findViewById(R.id.change_pass);
        reset = (Button) findViewById(R.id.reset);
        row1 = (TextView) findViewById(R.id.row1);
        row1.setBackgroundColor(Color.GRAY);
        row2 = (TextView) findViewById(R.id.row2);
        row2.setBackgroundColor(Color.GRAY);
        tv1 = (TextView) findViewById(R.id.tv1);
        int i = s_db.getStocksCount();
        
        float balance = user.getMoney();
        List<Statistic> stats = db.getAllStats(); 
        for (Statistic s: stats) {
        	balance = balance + s.getDelta();
        }	
        a = balance;
        Float ratio = (balance/user.getMoney()-1)*100;
        tv1.setText("- NUMBER OF PORTFOLIOS: "+i+ "\n\n- FUND: "+user.getMoney()+"\n- BALANCE: "+balance+"\n=> PROFIT: "+ String.format("%.4f", ratio)+"%");
        
        OnClickListener minus = new OnClickListener() {
        	
            public void onClick(View v) {
            	rut();
            }
        };
        withdraw.setOnClickListener(minus);
        
        OnClickListener plus = new OnClickListener() {
        	
            public void onClick(View v) {
            	them();
            }
        };
        deposit.setOnClickListener(plus);
        
        OnClickListener cp = new OnClickListener() {
        	
            public void onClick(View v) {
            	changePass();
            }
        };
        change.setOnClickListener(cp);
        
        OnClickListener reset_all = new OnClickListener() {
        	
            public void onClick(View v) {
            	reset();
            }
        };
        reset.setOnClickListener(reset_all);
	}
	
	public void rut () {
				
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(UserControl.this);
     	helpBuilder.setTitle("Withdraw");
     	helpBuilder.setMessage("Enter the amount of money:");
     	final EditText input = new EditText(UserControl.this);
     	input.setSingleLine();
     	input.setText("");
     	helpBuilder.setView(input);
     	helpBuilder.setPositiveButton("OK",
     	   new DialogInterface.OnClickListener() {
     	    public void onClick(DialogInterface dialog, int which) {
     	    	String temp = input.getText().toString();
      	    	if (temp.equals("")) {
      	    		Toast.makeText(getBaseContext(), "Please enter the amount of money!",Toast.LENGTH_LONG).show();
      	    		rut();
      	    	} else if (Float.parseFloat(temp)<=0) {
      	    		Toast.makeText(getBaseContext(), "Please enter a valid number!",Toast.LENGTH_LONG).show();
      	    		rut();
      	    	} else if (isFloat(temp)==false) {
      	    		Toast.makeText(getBaseContext(), "Not a number!",Toast.LENGTH_LONG).show();
      	    		rut();
      	    	} else if ((user.getMoney() - Float.parseFloat(temp))<0){
      	    		Toast.makeText(getBaseContext(), "Not enough money!",Toast.LENGTH_LONG).show();
      	    		rut();          	    		
      	    	} else {
      	    		//Got a valid number
      	    		Float money = Float.parseFloat(temp);
      	    		//Make change
      	    		user.setMoney(user.getMoney() - money);
      	    		u_db.updateUser(user);
      	    		user = u_db.getUser();
      	    		int i = s_db.getStocksCount();
      	    		a = a - money;
      	    		Float ratio = (a/user.getMoney()-1)*100;
      	    		tv1.setText("- NUMBER OF PORTFOLIOS: "+i+ "\n\n- FUND: "+user.getMoney()+"\n- BALANCE: "+a+"\n=> PROFIT: "+ String.format("%.4f", ratio)+"%");
      	    	}
     	   }
     	});
     	helpBuilder.setNegativeButton("Back",
      	   new DialogInterface.OnClickListener() {
      	    public void onClick(DialogInterface dialog, int which) {
      	    }
     	});
     	// Remember, create doesn't show the dialog
     	AlertDialog helpDialog = helpBuilder.create();
     	helpDialog.setCancelable(false);
     	helpDialog.show();
	}
	
	public void them () {
		
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(UserControl.this);
     	helpBuilder.setTitle("Deposit");
     	helpBuilder.setMessage("Enter the amount of money:");
     	final EditText input = new EditText(UserControl.this);
     	input.setSingleLine();
     	input.setText("");
     	helpBuilder.setView(input);
     	helpBuilder.setPositiveButton("OK",
     	   new DialogInterface.OnClickListener() {
     	    public void onClick(DialogInterface dialog, int which) {
     	    	String temp = input.getText().toString();
      	    	if (temp.equals("")) {
      	    		Toast.makeText(getBaseContext(), "Please enter the amount of money!",Toast.LENGTH_LONG).show();
      	    		rut();
      	    	} else if (Float.parseFloat(temp)<=0) {
      	    		Toast.makeText(getBaseContext(), "Please enter a valid number!",Toast.LENGTH_LONG).show();
      	    		rut();
      	    	} else if (isFloat(temp)==false) {
      	    		Toast.makeText(getBaseContext(), "Not a number!",Toast.LENGTH_LONG).show();
      	    		rut();          	    		
      	    	} else {
      	    		//Got a valid number
      	    		Float money = Float.parseFloat(temp);
      	    		//Make change
      	    		user.setMoney(user.getMoney() + money);
      	    		u_db.updateUser(user);
      	    		user = u_db.getUser();
      	    		int i = s_db.getStocksCount();
      	    		a = a + money;
      	    		Float ratio = (a/user.getMoney()-1)*100;
      	    		tv1.setText("- NUMBER OF PORTFOLIOS: "+i+ "\n\n- FUND: "+user.getMoney()+"\n- BALANCE: "+a+"\n=> PROFIT: "+ String.format("%.4f", ratio)+"%");
      	    	}
     	   }
     	});
     	helpBuilder.setNegativeButton("Back",
      	   new DialogInterface.OnClickListener() {
      	    public void onClick(DialogInterface dialog, int which) {
      	    }
     	});
     	// Remember, create doesn't show the dialog
     	AlertDialog helpDialog = helpBuilder.create();
     	helpDialog.setCancelable(false);
     	helpDialog.show();
	}
	
	public void changePass() {
    	
		 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
     	 helpBuilder.setTitle("Change password");
     	 helpBuilder.setMessage("Retype old password and enter new password:");
     	 final View layout = View.inflate(this, R.layout.change, null);
     	 final EditText input1 = ((EditText) layout.findViewById(R.id.text1));
     	 final EditText input2 = ((EditText) layout.findViewById(R.id.text2));
     	 helpBuilder.setIcon(0);
     	 helpBuilder.setPositiveButton("OK",
     	   new DialogInterface.OnClickListener() {
     	    public void onClick(DialogInterface dialog, int which) {
     	    	String pass1 = input1.getText().toString();
     	    	String pass2 = input2.getText().toString();
     	    	if (pass1.equals("")|(pass2.equals(""))) {
     	    		Toast.makeText(getBaseContext(), "Please enter both passwords!",Toast.LENGTH_LONG).show();
     	    		changePass();
     	    	} else if (pass1.equals(user.getPass())) {
     	    		u_db.updateUser(new User(pass2,user.getMoney(),user.getFirst(),true));
     	    		Toast.makeText(getBaseContext(), "Changed successfully",Toast.LENGTH_LONG).show();
     	    	} else {
     	    		Toast.makeText(getBaseContext(), "Invalid current password!",Toast.LENGTH_LONG).show();
     	    		changePass();
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
	
	public void reset () {
		
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(UserControl.this);
     	helpBuilder.setTitle("Warning");
     	helpBuilder.setMessage("This action will delete all of your data, include password, money, portfolios and history. Enter password to continue:");
     	final EditText input = new EditText(UserControl.this);
     	input.setSingleLine();
     	input.setText("");
     	helpBuilder.setView(input);
     	helpBuilder.setPositiveButton("OK",
     	   new DialogInterface.OnClickListener() {
     	    public void onClick(DialogInterface dialog, int which) {
     	    	String temp = input.getText().toString();
      	    	if (temp.equals(user.getPass())) {
      	    		 u_db.deleteAllUser();
      	    		 s_db.deleteAllStock();
      	    		 h_db.deleteAllHistory();
      	    		 db.deleteAllStat();
      	    		 int i = s_db.getStocksCount();
      	    		 tv1.setText("- NUMBER OF PORTFOLIOS: "+i+ "\n\n- FUND: null\n- BALANCE: null\n=> PROFIT: null%");
      	    		 Toast.makeText(getBaseContext(), "All info has been erased",Toast.LENGTH_LONG).show();
      	    		 AccManager.check=1;
      	    	} else {
      	    		Toast.makeText(getBaseContext(), "Invalid password!",Toast.LENGTH_LONG).show();
      	    	}
     	   }
     	});
     	helpBuilder.setNegativeButton("Back",
      	   new DialogInterface.OnClickListener() {
      	    public void onClick(DialogInterface dialog, int which) {
      	    }
     	});
     	// Remember, create doesn't show the dialog
     	AlertDialog helpDialog = helpBuilder.create();
     	helpDialog.setCancelable(false);
     	helpDialog.show();
	}
	
	public boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException nfe) {}
        return false;
    }
}
