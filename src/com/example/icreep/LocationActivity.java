package com.example.icreep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class LocationActivity extends FragmentActivity implements TabListener {

	ActionBar actionBar;
	ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);

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
		tab1.setText("Current Location");
		tab1.setTabListener(this);

		ActionBar.Tab tab2 = actionBar.newTab();
		tab2.setText("Visited Zones");
		tab2.setTabListener(this);

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
	}

	/**
	 * Queries the Location object to find which zone the user is currently in
	 * 
	 * @param location
	 *            - the location object
	 * @return zone - the current zone
	 */
	private int getCurrentZone(UserLocation location) {
		return 0;

	}

	/**
	 * Updates the displayed map with the current location of the user
	 */
	private void updateMap() {

	}

	/**
	 * Queries the Location object to determine where the user has been that day
	 * 
	 * @param location
	 *            - the location object
	 * @return zones - an array representing all of the zones the user has
	 *         visited that day
	 */
	private ArrayList<Integer> findDailyMovements(UserLocation location) {
		return null ;
	}

	/**
	 * Updates the table that displays all of the zones the user has been that
	 * day
	 */
	private void updateDailyTable() {

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
