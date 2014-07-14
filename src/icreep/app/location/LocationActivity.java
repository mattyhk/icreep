package icreep.app.location;

import icreep.app.ICreepApplication;
import icreep.app.R;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.Toast;

public class LocationActivity extends FragmentActivity implements TabListener {
	
	private static final int ENABLE_BLUETOOTH_REQUEST = 1;

	private ActionBar actionBar;
	private ViewPager viewPager;
	
	private ICreepApplication mApplication;
	private UserLocation user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		
		mApplication = (ICreepApplication) getApplicationContext();
		user = new UserLocation(this);

		viewPager = (ViewPager) findViewById(R.id.location_pager);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

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
		tab1.setText(getResources().getString(R.string.title_location_current));
		tab1.setTabListener(this);

		ActionBar.Tab tab2 = actionBar.newTab();
		tab2.setText(getResources().getString(R.string.title_location_movement));
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
}

class MyAdapter extends FragmentPagerAdapter {

	private final int NUMSCREENS = 2;
	
	// Provides reference to instantiated fragments, allows for call in onPageSelected
	private final Map<Integer, Fragment> mPageReferenceMap = new HashMap<Integer, Fragment>();

	public MyAdapter(FragmentManager fm) {
		super(fm);

	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;

		if (arg0 == 0) {
			fragment = new LocationFragmentA();
			mPageReferenceMap.put(arg0, fragment);
		}
		if (arg0 == 1) {
			fragment = new LocationFragmentB();
			mPageReferenceMap.put(arg0, fragment);
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
	
	public Fragment getFragment(int position){
		return mPageReferenceMap.get(position);
	}
}
