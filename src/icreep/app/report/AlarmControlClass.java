package icreep.app.report;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmControlClass {
	
	Intent myIntent = null ;
	PendingIntent pendingIntent = null ;
	AlarmManager alarmManager = null ;
	int minute = 60 * 1000;
	public AlarmControlClass() {
		// TODO Auto-generated constructor stub
		
	}
	
    public void setAlarm(int hour, int min, Context context){
//		String[] strTime;
//
//		strTime = time.split(":");
//
//		int hour, min, sec;
//		// set when to alarm
//		hour = Integer.valueOf(strTime[0]);
//		min = Integer.valueOf(strTime[1]);
//		sec = 0;

        long _alarm = 0;
        Calendar now = Calendar.getInstance();
        Calendar alarm = Calendar.getInstance();
        alarm.set(Calendar.HOUR_OF_DAY, hour);
        alarm.set(Calendar.MINUTE, min);
        alarm.set(Calendar.SECOND, 0);

        if(alarm.getTimeInMillis() <= now.getTimeInMillis())
            _alarm = alarm.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1);
        else
            _alarm = alarm.getTimeInMillis();               
        Date d = new Date();
        	        
      
        myIntent = new Intent(context, ListenToAlarmForAutoEmail.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent,0);
       
        alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC, _alarm, pendingIntent); //actual one
        alarmManager.set(AlarmManager.RTC, (d.getTime() + 1000), pendingIntent); // for testing purposes
       // alarmManager.setRepeating(AlarmManager.RTC, (d.getTime() + 1000), minute, pendingIntent); // for testing purposes
    }
    
    public void turnOffAlarm()
    {
    alarmManager.cancel(pendingIntent);	
    }
}
