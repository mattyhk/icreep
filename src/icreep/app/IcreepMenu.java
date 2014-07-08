package icreep.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import icreep.app.R;

public class IcreepMenu extends Activity {
	
	Button location_button, time_tracker_button, reports_button, profile_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icreep_menu);

		//Proximity Button
		location_button = (Button) findViewById(R.id.button1_location);
		location_button.setOnClickListener(new SwitchButtonListener(this, "icreep.app.location.LocationActivity"));
		
		//Time Tracker Button
		time_tracker_button = (Button) findViewById(R.id.button2_time_tracker);
		time_tracker_button.setOnClickListener(new SwitchButtonListener(this, "icreep.app.timetracker.TimeTrackerActivity"));
		
		//Reports Button
		reports_button = (Button) findViewById(R.id.button3_reports);
		reports_button.setOnClickListener(new SwitchButtonListener(this, "icreep.app.report.ReportActivity"));
		
		//Proximity Button
		profile_button = (Button) findViewById(R.id.button4_profile);
		profile_button.setOnClickListener(new SwitchButtonListener(this, "icreep.app.ProfileCreationActivity"));
		
	}//onCreate
	
}//IcreepMenu class
