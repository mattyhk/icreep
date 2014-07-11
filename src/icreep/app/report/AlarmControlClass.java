package icreep.app.report;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmControlClass
{

	Intent myIntent;
	PendingIntent pendingIntent ;
	PendingIntent manualIntent  ;
	AlarmManager alarmManager ;
	long _alarm = 0;
	Context c = null ;
	public AlarmControlClass()
	{
		// TODO Auto-generated constructor stub

	}

	// I am thinking of possibly removing this for two options, one for auto and
	// one for manual...
	// still deciding... will see if issues arrive
	// this class might be used for other alarm controls, if that arises, then
	// will reactor this class
	// to be adaptive

	/*
	 * Pre-Conditions: CAlled if you want to set the standards for the alarm
	 * Post-conditions: > This will set the alarm for the auto mailer.
	 */
	public void setAlarm(int hour, int min, Context context)
	{

		Calendar now = Calendar.getInstance();
		Calendar alarm = Calendar.getInstance();
		alarm.set(Calendar.HOUR_OF_DAY, hour);
		alarm.set(Calendar.MINUTE, min);
		alarm.set(Calendar.SECOND, 0);
		c = context;
		if (alarm.getTimeInMillis() <= now.getTimeInMillis())
			_alarm = alarm.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1);
		else
			_alarm = alarm.getTimeInMillis();

		myIntent = new Intent(context, AutoAlarmListener.class);
		pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
		manualIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);

		alarmManager = (AlarmManager) context
				.getSystemService(Activity.ALARM_SERVICE);
	}

	/*
	 * Pre-Conditions: This is called if the mail fails Post-conditions: > Will
	 * set another alarm to resend the email in 4 seconds time
	 */
	public void sendAutoEmailNow()
	{
		manualIntent = PendingIntent.getBroadcast(c, 0, myIntent, 0);
		alarmManager.set(AlarmManager.RTC, ((new Date()).getTime() + 4 * 1000),
				manualIntent);
	}

	/*
	 * Pre-Conditions: This is called every time the app starts to reset alarm
	 * for the auto generated email if the app closes Post-conditions: > Sets
	 * the alarm to occur at a certain time and every day at that time after
	 * that
	 */
	public void sendAutoEmailRepeat()
	{
		// for testing
		// alarmManager.setRepeating(AlarmManager.RTC, ((new Date()).getTime()+
		// 1000), AlarmManager.INTERVAL_DAY, pendingIntent);
		// actual
		alarmManager.setRepeating(AlarmManager.RTC, (_alarm),
				AlarmManager.INTERVAL_DAY, pendingIntent);
	}

	/*
	 * Pre-Conditions: This will be called if the current alarm needs
	 * cancellation Post-conditions: > turn off the current alarm > should think
	 * about the fact if they cancel, it will cancel the pending intent... which
	 * could be the auto mailer, could remove it and not want it removed
	 */
	public void turnOffAlarmAuto()
	{
//		myIntent = new Intent(c, AutoAlarmListener.class);
//		pendingIntent = PendingIntent.getBroadcast(c, 0, myIntent, 0);
		alarmManager.cancel(pendingIntent);
	}
	
	public void turnOffAlarmMan()
	{
		myIntent = new Intent(c, AutoAlarmListener.class);
		manualIntent = PendingIntent.getBroadcast(c, 0, myIntent, 0);
		alarmManager.cancel(pendingIntent);
	}
}
