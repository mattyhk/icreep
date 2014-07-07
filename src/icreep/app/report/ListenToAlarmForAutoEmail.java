package icreep.app.report;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ListenToAlarmForAutoEmail extends BroadcastReceiver{
	
	/*
	 * Pre-Conditions: This will be called from alarm manager
	 * Post-conditions: 
	 * >  Executes the attempt to email
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub		
		AttempToEmail mc = new AttempToEmail(context);	
			mc.execute("");		
	}

}
