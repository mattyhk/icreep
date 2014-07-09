package icreep.app.timetracker;

import java.util.ArrayList;

import icreep.app.Message;
import icreep.app.R;
import icreep.app.SharedPreferencesControl;
import icreep.app.SwitchButtonListener;
import icreep.app.db.iCreepDatabaseAdapter;
import icreep.app.report.Sorting;
import icreep.app.report.TimePlace;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TimeTrackerFragmentA extends Fragment implements OnItemClickListener {
	
	private int INTERVAL = 10000;
	
	private ListView listView = null;
	private ArrayList<TimePlace> timePlaces = new ArrayList<TimePlace>();
	
	private ImageButton home;

	int userID = -1;
	
	private iCreepDatabaseAdapter icreepHelper;
	private TimeTrackerListAdapter mAdapter;
	private Handler mHandler;
	
	public TimeTrackerFragmentA() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_time_tracker_a, container, false);
		
		TextView fragmentUser = (TextView) v.findViewById(R.id.time_tracker_a_user);

		SharedPreferencesControl spc = new SharedPreferencesControl(getActivity());
		userID = spc.getUserID();
		
		home = (ImageButton) v.findViewById(R.id.home_button_time_tracker_a);
		Activity c = getActivity();
		if (c != null) {
			home.setOnClickListener(new SwitchButtonListener(c, "icreep.app.IcreepMenu"));
		}

        //get time places that user has been to from the database       
        icreepHelper = new iCreepDatabaseAdapter(getActivity());
        
        String userDetails = icreepHelper.getUserDetails(userID);
    	fragmentUser.setText(userDetails);
    	
    	TimeTrackerActivity tTA =  (TimeTrackerActivity) this.getActivity();
        tTA.setUserDetails(userDetails);
        
        listView = (ListView) v.findViewById(R.id.time_tracker_listView_main);
		mAdapter = new TimeTrackerListAdapter(getActivity(), timePlaces);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        
        //get timePlaces (Description, totalTimeSpent, floor)        
        timePlaces = icreepHelper.getTimePlaces(userID);
               
        if(timePlaces != null){ 
        	updateList();
        }
        else{
        	fragmentUser.setText("Unknown User");        	
        	Message.message(getActivity(), "You haven't been anywhere");
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
		
		if(timePlaces != null){
			for(TimePlace tp: timePlaces){
				double time = tp.getTimeSpent();
				total+= time;
			}
		}
		
		//send this total to TimeTracker Activity so that fragment B may access it from there
		TimeTrackerActivity tTA = (TimeTrackerActivity) this.getActivity();
		tTA.setTime(total);		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		TimePlace zone = (TimePlace) timePlaces.get(position);
		Toast.makeText(getActivity(), "You clicked " + zone.getZoneID() , Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Needs to update the list displayed
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startRepeatingTask();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		stopRepeatingTask();
	}
	
	private Runnable mListUpdater = new Runnable() {
		
		@Override
		public void run() {
			updateList();
			mHandler.postDelayed(mListUpdater, INTERVAL);
		}
	};
	
	private void startRepeatingTask() {
		mListUpdater.run();
	}
	
	private void stopRepeatingTask() {
		mHandler.removeCallbacks(mListUpdater);
	}
	
	private void updateList() {
		
		mAdapter.clear();
		
		timePlaces = icreepHelper.getTimePlaces(userID);
        
        if(timePlaces != null){ 
        	//this function will sort the location with respect to their floors and description(locations)
        	timePlaces = sortTimePlaces(timePlaces); 
        	
        	//now add these TimePlaces into ListView
        	mAdapter.clear();
        	mAdapter.addAll(timePlaces);
        	mAdapter.notifyDataSetChanged();

        	//calculate and put Total-Time in Time Tracker Activity for Activity B
        	totalTime(timePlaces);
        }
		
	}
}
