package icreep.app.beacon;

import java.util.ArrayList;

import com.radiusnetworks.ibeacon.IBeacon;

import icreep.app.ICreepApplication;
import icreep.app.MainMenuActivity;
import icreep.app.Message;
import icreep.app.R;
import icreep.app.SharedPreferencesControl;
import icreep.app.SwitchButtonListener;
import icreep.app.location.UserLocation;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class BeaconSelectionActivity extends Activity {
	
	private final int NOT_SELECTED = -1;
	private static final int ENABLE_BLUETOOTH_REQUEST = 1;
	
	private TextView bossTrackingValue;
	private ListView beaconListView;
	private Switch switched;
	private Button save;
	private ImageButton home;
	private Button updateButton;
	
	private SharedPreferencesControl spc;
	private ICreepApplication mApplication;
	private BeaconListAdapter mAdapter;
	private UserLocation user;
	
	public int selectedIndex = NOT_SELECTED;
	private int currentBoss;
	private ArrayList<IBeacon> beaconList = new ArrayList<IBeacon>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_boss);
		
		ActionBar actionBar = getActionBar();
		actionBar.removeAllTabs();
		
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(false);
		
		spc = new SharedPreferencesControl(this);
		mApplication = (ICreepApplication) getApplicationContext();
		mAdapter = new BeaconListAdapter(this, beaconList);
		user = new UserLocation(this);
		
		bossTrackingValue = (TextView) findViewById(R.id.bossTrackingValue);
		switched = (Switch) findViewById(R.id.switchBarBoss);
		save = (Button) findViewById(R.id.bossSaveButton);
		beaconListView = (ListView) findViewById(R.id.boss_listview);
		updateButton = (Button) findViewById(R.id.bossScanButton);
		home = (ImageButton) findViewById(R.id.home_button_boss);
		home.setOnClickListener(new SwitchButtonListener(this,
				"icreep.app.MainMenuActivity"));
		
		currentBoss = Integer.parseInt(spc.getBossBeaconDetails());

		setCheckButton();
		
		switched.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// TODO Auto-generated method stub
				if (isChecked == true)
				{
					updateButton.setEnabled(true);
					updateButton.setBackground(getResources().getDrawable(R.drawable.reports_buttons_on));					
				}else
				{
					updateButton.setEnabled(false);
					updateButton.setBackground(getResources().getDrawable(R.drawable.reports_button_off));
				}
			}
		});
		
		beaconListView.setAdapter(mAdapter);
		beaconListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		beaconListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)	{
				// TODO Auto-generated method stub
				if (selectedIndex != position) {
					selectedIndex = position;
					currentBoss = beaconList.get(selectedIndex).getMinor();
					Log.d("TEST", "Selected Index is " + selectedIndex);
					mAdapter.notifyDataSetChanged();
					beaconListView.setItemChecked(position, true);
					 
					
			
					// mAdapter.notifyDataSetChanged();
				}
			}
		});
		
		save.setOnClickListener(new OnClickListener()	{
			
			@Override
			public void onClick(View v)	{
				
				if ((currentBoss != 0) && (switched.isChecked())) {
					switchOnBeaconBossTracking();
					return;
				}
				
				if (validateSave()) {
					saveBossDetails();
				}
			}
		});
		
		updateButton.setOnClickListener(new OnClickListener()	{
			
			@Override
			public void onClick(View v)	{
				updateList();
			}
		});
	}
	
	
	@Override
	public void onBackPressed() 
	{
	    Intent myIntent = new Intent(this, MainMenuActivity.class);
	    startActivity(myIntent);
	    super.onBackPressed();
	}
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
	
	
	public void updateList() {
		
		this.beaconList = (ArrayList<IBeacon>) mApplication.getBeaconList();
		
		mAdapter.clear();
		mAdapter.addAll(this.beaconList);
		mAdapter.notifyDataSetChanged();
		 
		selectedIndex = NOT_SELECTED;
	}
	
	private boolean validateSave()
	{
		if ((switched.isChecked() == false) && (currentBoss != 0))
		{
			return true;
		}
		
		if ((switched.isChecked()==false )&& (currentBoss == 0))
		{
			Message.message(this, "Please switch on tracking status and search for iBeacon");
			return false ;
		}
		if (selectedIndex == NOT_SELECTED)
		{
			Message.message(this, "Please select an item in order to save");
			return false ;
		}
		
		IBeacon selectedBeacon = this.beaconList.get(selectedIndex);

		if (currentBoss == selectedBeacon.getMinor())
		{
			Message.message(this, "The beacon being tracked has not changed");
			return false ;
		}	
		
		return true;
	}
	
	/**
	 * Gets the beacon currently pointed to by the selection index. Sets the 
	 * application tracking status to the value of the switch button.
	 */
	private void saveBossDetails() {
		
		if (switched.isChecked() == false) {
			bossTrackingValue.setText(String.valueOf(currentBoss));
			spc.writeBossBeaconDetails(String.valueOf(currentBoss));
			selectedIndex = NOT_SELECTED;
			mAdapter.clear();
			mAdapter.notifyDataSetChanged();			
			switchOffBeaconBossTracking();
			return;
		}
		
		if (selectedIndex != NOT_SELECTED) {
			Log.d("TEST", "Saving position at " + selectedIndex);
			bossTrackingValue.setText(String.valueOf(currentBoss));
			spc.writeBossBeaconDetails(String.valueOf(currentBoss));
			selectedIndex = NOT_SELECTED;
			mAdapter.notifyDataSetChanged();
			switchOnBeaconBossTracking();
			return;
		}
	}
	
	private void switchOffBeaconBossTracking() {
		Message.message(this, "Stopped Tracking Boss");
		mApplication.setTrackingBoss(false);
	}
	
	private void switchOnBeaconBossTracking() {
		bossTrackingValue.setText(String.valueOf(currentBoss));
		spc.writeBossBeaconDetails(String.valueOf(currentBoss));
		Message.message(this, "Tracking Boss Beacon " + currentBoss);
		mApplication.setBossID(currentBoss);
		mApplication.setTrackingBoss(true);
	}
	
	private void setCheckButton()
	{
		//boolean checked = false ;
		if (mApplication.getBossID() == 0) {
			this.currentBoss = 0;
			bossTrackingValue.setText("");
		}
		
		else {
			this.currentBoss = mApplication.getBossID();
			bossTrackingValue.setText(String.valueOf(currentBoss));
		}
		
		switched.setChecked(mApplication.isTrackingBoss());
		
		if (mApplication.isTrackingBoss()) {
			updateButton.setEnabled(true);
			updateButton.setBackground(getResources().getDrawable(R.drawable.reports_buttons_on));
		}
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

}
