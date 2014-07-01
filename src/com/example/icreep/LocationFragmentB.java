package com.example.icreep;


import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class LocationFragmentB extends Fragment {
	
	ListView l;
	ArrayList<String> secondFloor;
	ArrayList<String> firstFloor;
	ArrayList<String> groundFloor;

	public LocationFragmentB() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		secondFloor.add("Hallway");
		secondFloor.add("Boardroom");
		firstFloor.add("Stairs");
		firstFloor.add("Window");
		groundFloor.add("Gym");
		groundFloor.add("Kitchen");
		
		View v = inflater.inflate(R.layout.fragment_location_b, container, false);
		
		l = (ListView) getView().findViewById(R.id.location_b_listView1);
		
		// Temporary adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, secondFloor);
		
		// Inflate the layout for this fragment
		return v;
	}

}
