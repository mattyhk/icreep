package icreep.app.timetracker;

import icreep.app.R;
import icreep.app.SwitchButtonListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("DefaultLocale") public class TimeTrackerFragmentB extends Fragment {
	
	private int INTERVAL = 10000;
	
	private ProgressBar mProgressBar;
	private ImageButton home;
	private TextView fragmentUser;
	private TextView fragmentInTime;
	private TextView fragmentOutTime;
	
	private double timeIn = 0.0;
	private double timeOut = 24.0;

	private Handler mHandler; 
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
		
		timeIn = timeTracker.getTime();
		
		if(timeIn != 0){
			timeOut -= timeIn;
			
			// get percentage and set progress
			mProgressBar.setProgress(calcPercentageTime(timeIn));

			//display in & out of office times
			//In office hours and minutes
			String[] intime = (Double.toString(timeIn)).split(".");		
			int inHours = Integer.parseInt(intime[0]);		
			int inMinutes =  (int) (timeIn - inHours)*60;
			String inminutes = String.format("%02d",inMinutes);
					
			//Out office hours and minutes
			String[] outtime = (Double.toString(timeOut)).split(".");
			int outHours = Integer.parseInt(outtime[0]);		
			int outMinutes =  (int) (timeOut - outHours)*60;
			String outminutes = String.format("%02d",outMinutes);
			
			fragmentInTime.setText(inHours + ":" + inminutes);
			fragmentOutTime.setText(outHours + ":" + outminutes);
		}
		
		else{
			fragmentInTime.setText("0:00");
			fragmentOutTime.setText("24:00");
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
	private int calcPercentageTime(double inOffice) { 
		return (int) (inOffice/24)*100;
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
