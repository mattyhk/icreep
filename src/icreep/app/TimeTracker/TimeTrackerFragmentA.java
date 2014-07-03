package icreep.app.timetracker;

import java.util.ArrayList;

import icreep.app.R;
import icreep.app.SwitchButtonListener;
import icreep.app.location.FloorItem;
import icreep.app.location.ListItem;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TimeTrackerFragmentA extends Fragment implements OnItemClickListener {
	
	private ListView listView = null;
	private ArrayList<ListItem> items = new ArrayList<ListItem>(); 
	private TimeTrackerListAdapter mAdapter;
	private ImageButton home;

	public TimeTrackerFragmentA() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_time_tracker_a, container, false);
		
		TextView fragmentTitle = (TextView) v.findViewById(R.id.time_tracker_a_title);
		TextView fragmentUser = (TextView) v.findViewById(R.id.time_tracker_a_user);
		
		float correctTextSize = 16*getResources().getDisplayMetrics().density;
		fragmentTitle.setTextSize(correctTextSize);
		fragmentUser.setTextSize(correctTextSize);
		
		listView = (ListView) v.findViewById(R.id.time_tracker_listView_main);
		
		items.add(new FloorItem("Ground Floor"));
        items.add(new ZoneTimeItem("Gym", "South", "1:00"));
        items.add(new ZoneTimeItem("Kitchen", "South", "2:00"));
        items.add(new ZoneTimeItem("Boardroom", "West", "1:50"));
        
        items.add(new FloorItem("First Floor"));
        items.add(new ZoneTimeItem("Boardroom", "South", "1:00"));
        items.add(new ZoneTimeItem("Wing", "East", "3:00"));
        
        items.add(new FloorItem("Second Floor"));
        items.add(new ZoneTimeItem("Boardroom", "South", "4:00"));
        items.add(new ZoneTimeItem("Wing", "East", "1:00"));
        
        mAdapter = new TimeTrackerListAdapter(getActivity(), items);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        
        home = (ImageButton) v.findViewById(R.id.home_button_time_tracker_a);
		Activity c = getActivity();
		if (c != null) {
			home.setOnClickListener(new SwitchButtonListener(c, "icreep.app.IcreepMenu"));
		}
		
		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		ZoneTimeItem item = (ZoneTimeItem)items.get(position);
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
