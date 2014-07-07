package icreep.app.timetracker;

import java.util.ArrayList;

import icreep.app.Message;
import icreep.app.R;
import icreep.app.SwitchButtonListener;
import icreep.app.db.iCreepDatabaseAdapter;
import icreep.app.location.FloorItem;
import icreep.app.location.ListItem;
import icreep.app.report.Sorting;
import icreep.app.report.TimePlace;
import icreep.app.report.ReportActivity;
import icreep.app.report.ReportManualFragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
	
	iCreepDatabaseAdapter icreepHelper;
	
	//list to store timePlaces
	public ArrayList<TimePlace> timePlaces;
	FragmentActivity fragActivity;
	
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
        
        //get time places that user has been to from the database       
        fragActivity = getActivity();
        icreepHelper = new iCreepDatabaseAdapter(fragActivity);
       
        //get timePlaces, tp (Description, totalTimeSpent, floor)        
        timePlaces = icreepHelper.getTimePlaces(); 
        if(timePlaces.size() != 0){
        	//this function will sort the location with respect to their floors and description(locations)
        	timePlaces = sortTimePlaces(timePlaces); 
        	
        	//now add these TimePlaces into ListView
        	
        	
        	//store Total-Time in bundle OR whatever for next fragment
        	totalTime(timePlaces);
        }
        else{
        	Message.message(fragActivity, "You haven't been anywhere");
        }
        
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
	
	/*
	 * Pre-Conditions: > ArrayList of time places that is unsorted according location(description), floor and total time spent.
	 * Post-conditions: > Sorts the arrayList of time places according location(description), floor and total time spent> The sort is done
	 */
	public ArrayList<TimePlace> sortTimePlaces(ArrayList<TimePlace> timePlaces){
		
		ArrayList<TimePlace> finalSortedTimePlaces = new ArrayList<TimePlace>();
		
		Sorting sorter = new Sorting();
		ArrayList<TimePlace> sorted = sorter.InsertionSort(timePlaces);
		
		TimePlace toAdd = sorted.get(0);
		sorted.remove(0);
		
		for(TimePlace tp : sorted){
			if(tp.getFloor().equals(toAdd.getFloor()) && tp.getLocation().equals(toAdd.getLocation())){
				toAdd.increaseTimeSpent(tp.getTimeSpent());				
			} 
			else{
				sorted.add(toAdd);
				toAdd=tp;
			}
		}	
		return finalSortedTimePlaces;
	}
	
	/*
	 * Pre-Conditions: > ArrayList of time places that is sorted according location(description), floor and total time spent.
	 * Post-conditions: > this function will return the total time spent in all the floors, locations(Descriptions)
	 */
	public void totalTime(ArrayList<TimePlace> timePlaces){
		
		double total = 0;
		
		for(TimePlace tp: timePlaces){
			double time = tp.getTimeSpent();
			total+= time;
		}		
		
		//send this total to TimeTracker Activity so that fragment B may access it from there
		TimeTrackerActivity tTA = (TimeTrackerActivity) this.getActivity();
		tTA.setTime(total);		
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
