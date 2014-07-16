package icreep.app.timetracker;

import java.util.ArrayList;

import icreep.app.ICreepApplication;
import icreep.app.Message;
import icreep.app.R;
import icreep.app.SwitchButtonListener;
import icreep.app.db.iCreepDatabaseAdapter;
import icreep.app.location.UserLocation;
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

public class TimeTrackerFragmentA extends Fragment implements OnItemClickListener {
	
	private final int INTERVAL = 5000;
	
	private ListView listView;
	private ArrayList<TimePlace> timePlaces = new ArrayList<TimePlace>();
	
	private ImageButton home;

	private int userID;
	
	private iCreepDatabaseAdapter icreepHelper;
	private TimeTrackerListAdapter mAdapter;
	private Handler mHandler = new Handler();
	private ICreepApplication mApplication;
	private UserLocation user;
	
	public TimeTrackerFragmentA() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_time_tracker_a, container, false);
		
		mApplication = (ICreepApplication) getActivity().getApplicationContext();
		user = new UserLocation(getActivity());
		
		//get time places that user has been to from the database       
        icreepHelper = new iCreepDatabaseAdapter(getActivity());
        mAdapter = new TimeTrackerListAdapter(getActivity(), timePlaces);
		
		TextView fragmentUser = (TextView) v.findViewById(R.id.time_tracker_a_user);
		
		userID = user.getUserID();
		
		home = (ImageButton) v.findViewById(R.id.home_button_time_tracker_a);
		Activity c = getActivity();
		if (c != null) {
			home.setOnClickListener(new SwitchButtonListener(c, "icreep.app.MainMenuActivity"));
		}

        String userDetails = icreepHelper.getUserDetails(userID);
    	fragmentUser.setText(userDetails);
    	
    	TimeTrackerActivity tTA =  (TimeTrackerActivity) this.getActivity();
        tTA.setUserDetails(userDetails);
        
        listView = (ListView) v.findViewById(R.id.time_tracker_listView_main);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        
		return v;
	}
	
	/*
	 * Pre-Conditions: > ArrayList of time places that is sorted according location(description), floor and total time spent.
	 * Post-conditions: > this function will return the total time spent in all the floors, locations(Descriptions)
	 */
	public void totalTime(ArrayList<TimePlace> timePlaces){
		
		double total = user.getTotalTime();
		double totalIn = user.getInOfficeTime();
		double totalOut = user.getOutOfficeTime();
		
		//send this total to TimeTracker Activity so that fragment B may access it from there
		TimeTrackerActivity mActivity = (TimeTrackerActivity) this.getActivity();
		mActivity.setTotalTime(total);
		mActivity.setInTime(totalIn);
		mActivity.setOutTime(totalOut);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		}
	
	/**
	 * Needs to update the list displayed
	 */
	@Override
	public void onResume() {
		super.onResume();
		startRepeatingTask();
	}
	
	@Override
	public void onPause() {
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
		
		timePlaces = (ArrayList<TimePlace>) user.getVisitedZones();
		
		if (timePlaces.size() > 0){
			totalTime(timePlaces);
			for (TimePlace tp : timePlaces){
				mAdapter.add(tp);
			}
		}
		
		else if (mApplication.getCurrentLocation() == -2 && timePlaces.size() == 0) {
			Message.message(getActivity(), "You have yet to visit a zone");
		}
		
		mAdapter.notifyDataSetChanged();
	
	}
}
