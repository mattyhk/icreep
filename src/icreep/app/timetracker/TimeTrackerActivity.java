package icreep.app.timetracker;

import icreep.app.R;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

public class TimeTrackerActivity extends FragmentActivity implements TabListener {
	
	ActionBar actionBar;
	ViewPager viewPager;
	
	private double totalInTime = 0;
	private String userDetails;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_tracker);

		viewPager = (ViewPager) findViewById(R.id.time_tracker_pager);
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
		return totalInTime;
	}
	
	public void setTime(double t){
		totalInTime = t;
	}
	
	public void setUserDetails(String ud){
		userDetails = ud;
	}
	
	public String getUserDetails(){
		return userDetails;
	}
	
	

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