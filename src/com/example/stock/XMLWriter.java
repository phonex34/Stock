package com.example.stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class XMLWriter {

	public XMLWriter(List<Owned_Stock> stocks) {
		File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/stock.xml");
		try {
			newxmlfile.createNewFile();
		}catch(IOException e){
            Log.e("IOException", "exception in createNewFile() method");
		}
		
		//we have to bind the new file with a FileOutputStream
		FileOutputStream fileos = null;        
		try{
            fileos = new FileOutputStream(newxmlfile);
		}catch(FileNotFoundException e){
            Log.e("FileNotFoundException", "can't create FileOutputStream");
		}
		
		//we create a XmlSerializer in order to write xml data
		XmlSerializer serializer = Xml.newSerializer();
		try {
			 //we set the FileOutputStream as output for the serializer, using UTF-8 encoding
			serializer.setOutput(fileos, "UTF-8");
			//Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
			serializer.startDocument(null, Boolean.valueOf(true));
			//set indentation option
			serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
		} catch (Exception e) {
        	Log.e("Exception","error occurred while creating xml file");
        }
		
		for (Owned_Stock stock : stocks) 
		try {
			//start a tag called "root"
			serializer.startTag(null, "stock");
                           
			serializer.startTag(null, "Symbol");
			serializer.attribute(null, "data", stock.getName());
			serializer.endTag(null, "Symbol");
			
			serializer.startTag(null, "Company");
			serializer.attribute(null, "data", stock.getCompany());
			serializer.endTag(null, "Company");
			
			serializer.startTag(null, "Last");
			serializer.attribute(null, "data", Float.toString(stock.getBuyPrice()));
			serializer.endTag(null, "Last");
			
			serializer.startTag(null, "High");
			serializer.attribute(null, "data", Float.toString(stock.getHigh()));
			serializer.endTag(null, "High");
			
			serializer.startTag(null, "Low");
			serializer.attribute(null, "data", Float.toString(stock.getHigh()));
			serializer.endTag(null, "Low");
			
			serializer.startTag(null, "Number");
			serializer.attribute(null, "data", Integer.toString(stock.getNumber()));
			serializer.endTag(null, "Number");
			
			serializer.startTag(null, "Statistic");
			serializer.attribute(null, "data", Float.toString(stock.getReview()));
			serializer.endTag(null, "Statistic");
			
			serializer.endTag(null, "stock");
			
			
            } catch (Exception e) {
            	Log.e("Exception","error occurred while creating xml file");
            }
		
		try {
			serializer.endDocument();
			//write xml data into the FileOutputStream
			serializer.flush();
			//finally we close the file stream
			fileos.close();
		} catch (Exception e) {
			Log.e("Exception","error occurred while creating xml file");
		}
	}
}
