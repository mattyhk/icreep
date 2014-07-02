package com.example.icreep;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LocationFragmentA extends Fragment {

	public LocationFragmentA() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		View v = inflater.inflate(R.layout.fragment_location_a, container, false);
		
		return v;
	}
	
	/**
	 * Returns the current floor of the user
	 * @return floor
	 */
	private int getFloor(){
		return 0;
	}
	
	/**
	 * Sets the map to be displayed as determined by the current zone and floor
	 * @param floor
	 * @param currentZone
	 */
	private void setImage(int floor, int currentZone){
		
	}
	
	/**
	 * Needs to update the map displayed
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	

}
