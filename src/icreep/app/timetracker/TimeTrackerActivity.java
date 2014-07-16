package icreep.app.timetracker;

import icreep.app.ICreepApplication;
import icreep.app.MainMenuActivity;
import icreep.app.R;
import icreep.app.location.UserLocation;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.Toast;

public class TimeTrackerActivity extends FragmentActivity implements TabListener {
	
	private static final int ENABLE_BLUETOOTH_REQUEST = 1;
	
	private ActionBar actionBar;
	private ViewPager viewPager;
	private UserLocation user;
	private ICreepApplication mApplication;
	
	private double totalInTime = 0;
	private double totalOutTime = 0;
	private double totalTime = 0;
	private String userDetails;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_tracker);
		
		user = new UserLocation(this);
		mApplication = (ICreepApplication) getApplicationContext();

		viewPager = (ViewPager) findViewById(R.id.time_tracker_pager);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		actionBar = getActionBar();
		actionBar.removeAllTabs();
		
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(false);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.Tab tab1 = actionBar.newTab();
		tab1.setText(getResources().getString(R.string.time_in_locations));
		tab1.setTabListener(this);

		ActionBar.Tab tab2 = actionBar.newTab();
		tab2.setText(getResources().getString(R.string.outside));
		tab2.setTabListener(this);

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
	}



	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
	
	public double getTime(){
		return totalTime;
	}
	
	public double getInTime(){
		return totalInTime;
	}
	
	public double getOutTime(){
		return totalOutTime;
	}
	
	public void setTotalTime(double t){
		totalTime = t * 3600.0;
	}
	
	public void setInTime(double t){
		totalInTime = t * 3600.0;
	}
	
	public void setOutTime(double t){
		totalOutTime = t * 3600.0;
	}
	
	public void setUserDetails(String ud){
		userDetails = ud;
	}
	
	public String getUserDetails(){
		return userDetails;
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
	public void onBackPressed() 
	{
	    Intent myIntent = new Intent(this, MainMenuActivity.class);
	    startActivity(myIntent);
	    super.onBackPressed();
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
	
	

}

class MyAdapter extends FragmentPagerAdapter {

	private final int NUMSCREENS = 2;

	public MyAdapter(FragmentManager fm) {
		super(fm);

	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;

		if (arg0 == 0) {
			fragment = new TimeTrackerFragmentA();
		}
		if (arg0 == 1) {
			fragment = new TimeTrackerFragmentB();
		}

		return fragment;
	}

	@Override
	public int getCount() {
		return NUMSCREENS;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}
	
	
}