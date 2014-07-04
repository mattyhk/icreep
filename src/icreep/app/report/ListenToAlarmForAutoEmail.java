package icreep.app.report;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ListenToAlarmForAutoEmail extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub		
		AttempToEmail mc = new AttempToEmail(context);	
			mc.execute("");	
		
	}

}
