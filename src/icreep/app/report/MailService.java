package icreep.app.report;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class MailService extends AsyncTask<String, Void, String>
{

	private Context c;
	AlarmControlClass acc = new AlarmControlClass();

	public MailService(Context in)
	{
		c = in;
	}

	/*
	 * Pre-Conditions: This will be called from the broadcast receiver
	 * Post-conditions: > Send auto generated email > if fail, will try again.
	 * The usual reason for failing is connection time out
	 */
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(String... params)
	{
		// TODO Auto-generated method stub
		MailerClass mail = new MailerClass(c);

		boolean sent = false ;
		try {
			sent =mail.sendAutoMail();
			if (sent == true) {
				acc.setAlarm(0, 0, c);
				acc.turnOffAlarmMan();
				Log.e("vince", "THE MAIL WAS SENT YAY");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (sent == false)
			{
			acc.setAlarm(0, 0, c);
			acc.sendAutoEmailNow();
			}
			e.printStackTrace();
			Log.e("vince", "Couldn't send, sorry");

		}

		// acc.alarmManager.cancel(acc.pendingIntent);
		// Log.e("vince", "THE MAIL WAS SENT YAY"); // this might not be
		// necessary :P only time will tell
		return "";
	}

}
