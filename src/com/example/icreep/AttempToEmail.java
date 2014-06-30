package com.example.icreep;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.AsyncTask;
import android.util.Log;

public class AttempToEmail extends AsyncTask<String, Void, String>{

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {   
            GMailSender sender = new GMailSender("hobolicious101@gmail.com", "50837123");
            Mail m = new Mail("hobolicious101@gmail.com","50837123");
            DateFormat form =  new SimpleDateFormat("yyyy/MM/dd");
        	Date date = new Date() ;
        	String s = form.format(date);
            m.setBody("This is a test" + s);
            m.setFrom("hobolicious101@gmail.com");
            String[] to = new String[3];
            to[0] = "hobolicious101@gmail.com";
            to[1] = "vreid@openboxsoftware.com";
            to[2] = "scox127@gmail.com";
            
            m.setTo(to);
            m.setSubject("Testing this chaos");
            if( m.send() == true)
            {
            	System.exit(0);
            }
            
            
            
            sender.sendMail("This is cool Subject",   
                    "this is the testing of an automatic emailer",   
                    "hobolicious101@gmail.com",   
                    "vreid@openboxsoftware.com,hobolicious101@gmail.com");   
            //Log.i("vince", "email was sent");
        } catch (Exception e) {   
            Log.i("vince", e.getMessage(), e);   
        }
		return null; 
		
	}

}
