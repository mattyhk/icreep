package icreep.app;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import icreep.app.R;
import icreep.app.db.iCreepDatabaseAdapter;
import icreep.app.report.AlarmControlClass;

public class MainActivity extends FragmentActivity
{

	private static final int ENABLE_BLUETOOTH_REQUEST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferencesControl spc = new SharedPreferencesControl(this);
		iCreepDatabaseAdapter adapt = new iCreepDatabaseAdapter(this);
		ActionBar actionBar = getActionBar();
		actionBar.removeAllTabs();
		
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(false);
		//adapt.clearDatabase();
		//spc.clearSP(); // testing purposes
		// Check for Bluetooth capability
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			finishActivityWithMessage("Device does not support Bluetooth LE");
		}
		
		if (spc.sharedPrefTest() == true) {
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
			i.setClassName(this, "icreep.app.ProfileCreationActivity");
			startActivity(i);
		} 
		
		else {
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
	protected void onPause()
	{
		super.onPause();

		unregisterBluetoothReceiver();
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

	private void finishActivityWithMessage(String message)
	{
		// Notify the user of the problem
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
		toast.show();

		// End the activity
		finish();
	}

	/*********************
	 * 
	 * Bluetooth Checker Makes Sure the Bluetooth functionality is on. May need
	 * to move it into every activity
	 * 
	 ********************/

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode)
		{
			case ENABLE_BLUETOOTH_REQUEST:
				if (resultCode != Activity.RESULT_OK) {
					finishActivityWithMessage("Bluetooth must be on");
				}
				break;
			default:
				break;
		}
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

}
