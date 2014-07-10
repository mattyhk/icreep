package icreep.app.timetracker;

import icreep.app.R;
import icreep.app.SwitchButtonListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("DefaultLocale") public class TimeTrackerFragmentB extends Fragment {
	
	private int INTERVAL = 10000;
	private double TIMEOUT = 8.0 * 60 * 60;
	
	private ProgressBar mProgressBar;
	private ImageButton home;
	private TextView fragmentUser;
	private TextView fragmentInTime;
	private TextView fragmentOutTime;
	
	private double timeIn = 0.0;
	

	private Handler mHandler = new Handler(); 
	private TimeTrackerActivity timeTracker;
	
	public TimeTrackerFragmentB() {
		// Required empty public constructor
	}

	@SuppressLint("DefaultLocale") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_time_tracker_b, container, false);
		
		fragmentUser = (TextView) v.findViewById(R.id.time_tracker_b_user);
		fragmentInTime = (TextView) v.findViewById(R.id.time_in_office);
		fragmentOutTime = (TextView) v.findViewById(R.id.time_out_office);
		
		//float correctTextSize = 16*getResources().getDisplayMetrics().density;
		//fragmentTitle.setTextSize(correctTextSize);
		//fragmentUser.setTextSize(correctTextSize);
		
		mProgressBar = (ProgressBar) v.findViewById(R.id.time_tracker_progress_bar);
		mProgressBar.setMax(100);

		timeTracker = (TimeTrackerActivity) this.getActivity();		

		//get user details
		fragmentUser.setText(timeTracker.getUserDetails());
		
		updateBar();
		
		home = (ImageButton) v.findViewById(R.id.home_button_time_tracker_b);
		Activity c = getActivity();
		if (c != null) {
			home.setOnClickListener(new SwitchButtonListener(c, "icreep.app.IcreepMenu"));
		}				
		return v;
	}
	
	private void updateBar() {
		
		if (timeIn != timeTracker.getTime()) {
			
			timeIn = timeTracker.getTime() * 60 * 60;
			
			if(timeIn != 0){
				double timeOutTemp = TIMEOUT - timeIn;
				
				// get percentage and set progress
				mProgressBar.setProgress(0);
				mProgressBar.setProgress((int) calcPercentageTime(timeIn));
				
				
				int secondsIn = (int) (timeIn % 60);
				int minutesIn = (int) ((timeIn / 60) % 60);
				int hoursIn = (int) ((timeIn / 3600) % 60);
				
	
				int secondsOut = (int) (timeOutTemp % 60);
				int minutesOut = (int) ((timeOutTemp / 60) % 60);
				int hoursOut = (int) ((timeOutTemp / 3600) % 60);
				
				fragmentInTime.setText(String.format
						("%02d:%02d:%02d", hoursIn, minutesIn, secondsIn));
				fragmentOutTime.setText(String.format
						("%02d:%02d:%02d", hoursOut, minutesOut, secondsOut));
			}
			
			else{
				fragmentInTime.setText("00:00:00");
				fragmentOutTime.setText("24:00:00");
			}
		}
		
	}
	
	/**
	 * Needs to update the map displayed - should be updated in TimeTracker Activity upon fragment selection
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
	
	/**
	 * Calculates the percentage of time the user has spent in the office
	 * @param inOffice - time spent in the office
	 * @return percentage - the percentage rounded to nearest integer
	 */
	private double calcPercentageTime(double inOffice) {
		inOffice = inOffice % (8.0 * 60 * 60);
		return (inOffice/TIMEOUT)*100.0;
	}
	
	private Runnable mBarUpdater = new Runnable() {
		
		@Override
		public void run() {
			updateBar();
			mHandler.postDelayed(mBarUpdater, INTERVAL);
		}
	};
	
	private void startRepeatingTask() {
		mBarUpdater.run();
	}
	
	private void stopRepeatingTask() {
		mHandler.removeCallbacks(mBarUpdater);
	}


}
