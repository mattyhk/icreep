package com.example.icreep;


import java.util.ArrayList;

import com.example.icreep.R;
import com.example.icreep.R.id;
import com.example.icreep.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class LocationFragmentB extends Fragment implements OnItemClickListener {
	
	private ListView listView = null;
	private ArrayList<ListItem> items = new ArrayList<ListItem>(); 
	private DailyMovementAdapter mAdapter;

	public LocationFragmentB() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_location_b, container, false);
		
		TextView fragmentTitle = (TextView) v.findViewById(R.id.location_b_title);
		TextView fragmentUser = (TextView) v.findViewById(R.id.location_b_user);
		
		float correctTextSize = 16*getResources().getDisplayMetrics().density;
		fragmentTitle.setTextSize(correctTextSize);
		fragmentUser.setTextSize(correctTextSize);
		
		listView = (ListView) v.findViewById(R.id.daily_movement_listView_main);
		
		items.add(new FloorItem("Ground Floor"));
        items.add(new ZoneItem("Gym", "South"));
        items.add(new ZoneItem("Kitchen", "South"));
        items.add(new ZoneItem("Boardroom", "West"));
        items.add(new ZoneItem("Hallway", "North"));
        
        items.add(new FloorItem("First Floor"));
        items.add(new ZoneItem("Boardroom", "South"));
        items.add(new ZoneItem("Wing", "East"));
        items.add(new ZoneItem("Hallway", "Main"));
        
        items.add(new FloorItem("Second Floor"));
        items.add(new ZoneItem("Boardroom", "South"));
        items.add(new ZoneItem("Wing", "East"));
        items.add(new ZoneItem("Hallway", "Main"));
		
        mAdapter = new DailyMovementAdapter(getActivity(), items);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		ZoneItem item = (ZoneItem)items.get(position);
		Toast.makeText(getActivity(), "You clicked " + item.getTitle() , Toast.LENGTH_SHORT).show();
		
	}
	
	/**
	 * Needs to update the list displayed
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
