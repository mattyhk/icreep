package icreep.app;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import icreep.app.R;
import icreep.app.beacon.BeaconService;
import icreep.app.location.UserLocation;

public class MainMenuActivity extends Activity {
	
	private static final int ENABLE_BLUETOOTH_REQUEST = 1;

	private ICreepApplication mApplication;
	private UserLocation user;

	private Button location_button, time_tracker_button, reports_button, profile_button,boss_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icreep_menu);
		user = new UserLocation(this);
		mApplication = (ICreepApplication) getApplicationContext();
		ActionBar actionBar = getActionBar();
		actionBar.removeAllTabs();
		
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(false);
		if (!mApplication.hasStartedRanging()) {

			Intent trackingIntent = new Intent(this, BeaconService.class);
			startService(trackingIntent);
			mApplication.startedRanging();
			
		}
			
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
		
		//Boss button
		boss_button = (Button)findViewById(R.id.button5_boss_tracker);
		boss_button.setOnClickListener(new SwitchButtonListener(this, "icreep.app.BeaconSelectionActivity"));
	}//onCreate
	
	@Override
	protected void onResume() {
		super.onResume();
		
		registerBluetoothReceiver();

		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterBluetoothReceiver();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		user.updateLocationOnDestroy(mApplication.getCurrentLocation(), mApplication.getLastEntryID());
	}
	
	/*********************
	 * 
	 * Bluetooth Checker Makes Sure the Bluetooth functionality is on. May need
	 * to move it into every activity
	 * 
	 ********************/
	private void finishActivityWithMessage(String message)
	{
		// Notify the user of the problem
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
		toast.show();

		// End the activity
		finish();
	}

	private void registerBluetoothReceiver()
	{
		final IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

		registerReceiver(this.bluetoothChangedReceiver, filter);
	}

	private void unregisterBluetoothReceiver()
	{
		unregisterReceiver(this.bluetoothChangedReceiver);
	}

	private BroadcastReceiver bluetoothChangedReceiver = new BroadcastReceiver()
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{

			final String action = intent.getAction();
			if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				final int state = intent.getIntExtra(
						BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
				switch (state)
				{
					case BluetoothAdapter.STATE_TURNING_ON:
						break;
					case BluetoothAdapter.STATE_ON:
						break;
					case BluetoothAdapter.STATE_TURNING_OFF:
						finishActivityWithMessage("Requires Bluetooth");
						break;
					case BluetoothAdapter.STATE_OFF:
						break;
					case BluetoothAdapter.ERROR:
						finishActivityWithMessage("Bluetooth Error");
						break;
				}
			}
		}
	};
	
}//MainMenuActivity class
