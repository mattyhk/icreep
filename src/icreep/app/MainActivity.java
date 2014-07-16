package icreep.app;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import icreep.app.R;
import icreep.app.db.iCreepDatabaseAdapter;
import icreep.app.report.AlarmControlClass;

public class MainActivity extends FragmentActivity
{	
	private ICreepApplication mApplication; 

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mApplication = (ICreepApplication) getApplicationContext();
		SharedPreferencesControl spc = new SharedPreferencesControl(this);
		iCreepDatabaseAdapter adapt = new iCreepDatabaseAdapter(this);
		ActionBar actionBar = getActionBar();
		actionBar.removeAllTabs();
		
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(false);
		
//		adapt.clearDatabase();
//		spc.clearSP(); // testing purposes

		if (spc.hasNoUser() == true) {
			Intent i = new Intent();
			i.setClassName(this, "icreep.app.ProfileCreationActivity");
			startActivity(i);
		} 
		
		else {
			mApplication.setBossID(Integer.parseInt(spc.getBossBeaconDetails()));
			
			String time = "";
			time = adapt.getReportTime(spc.getUserID());
			if (time != null) {
				String[] times = time.split(":");
				int hour = Integer.parseInt(times[0]);
				int min = Integer.parseInt(times[1]);
				AlarmControlClass acc = new AlarmControlClass();
				acc.setAlarm(hour, min, this);
				acc.sendAutoEmailRepeat();
				Message.message(this, "Alarm is set");
			}
			
			Intent i = new Intent();
			i.setClassName(this, "icreep.app.MainMenuActivity");
			startActivity(i);
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

	}

	@Override
	protected void onPause()
	{
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle out)
	{
		super.onSaveInstanceState(out);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

	}
}
