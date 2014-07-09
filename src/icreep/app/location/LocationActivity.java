package icreep.app.location;

import icreep.app.R;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

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
