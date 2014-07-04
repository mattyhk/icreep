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
	
	public AttempToEmail(Context in)
	{
		c = in ;
	}
	
	@Override
	protected String doInBackground(String... params) {		
		// TODO Auto-generated method stub
		MailerClass mail = new MailerClass();
		try {
			mail.sendAutoMail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("vince", "Coudln't send, sorry");
		}
		
		return "" ;
	}

}
