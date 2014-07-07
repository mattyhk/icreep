package icreep.app.report;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AttempToEmail extends AsyncTask<String, Void, String>{

	private Context c ;
	AlarmControlClass acc = new AlarmControlClass() ;
	public AttempToEmail(Context in)
	{
		c = in ;
	}
	
	
	/*
	 * Pre-Conditions: This will be called from the broadcast receiver 
	 * Post-conditions: 
	 * >  Send auto generated email
	 * >  if fail, will try again. The usual reason for failing is connection time out
	 * 
	 */
	@Override
	protected String doInBackground(String... params) {		
		// TODO Auto-generated method stub
		MailerClass mail = new MailerClass();
		try {
			if (mail.sendAutoMail(c) == true)
			{
				acc.alarmManager.cancel(acc.pendingIntent);
				Log.e("vince", "THE MAIL WAS SENT YAY");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			acc.setAlarm(0, 0, c);
			acc.sendAutoEmailNow();
			e.printStackTrace();
			Log.e("vince", "Couldn't send, sorry");
			
		}
		
//		acc.alarmManager.cancel(acc.pendingIntent);
//		Log.e("vince", "THE MAIL WAS SENT YAY"); // this might not be necessary :P only time will tell
		return "" ;
	}

}
