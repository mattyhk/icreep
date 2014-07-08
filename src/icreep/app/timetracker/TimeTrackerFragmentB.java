package icreep.app.timetracker;

import icreep.app.R;
import icreep.app.SwitchButtonListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("DefaultLocale") public class TimeTrackerFragmentB extends Fragment {
	
	private ProgressBar mProgressBar;
	private ImageButton home;
	
	public double inTimeHolder = 0;
	public double outTimeHolder = 24.0;

	public TimeTrackerFragmentB() {
		// Required empty public constructor
	}

	@SuppressLint("DefaultLocale") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_time_tracker_b, container, false);
		
		TextView fragmentTitle = (TextView) v.findViewById(R.id.time_tracker_b_title);
		TextView fragmentUser = (TextView) v.findViewById(R.id.time_tracker_b_user);
		TextView fragmentInTime = (TextView) v.findViewById(R.id.time_in_office);
		TextView fragmentOutTime = (TextView) v.findViewById(R.id.time_out_office);
		
		//float correctTextSize = 16*getResources().getDisplayMetrics().density;
		//fragmentTitle.setTextSize(correctTextSize);
		//fragmentUser.setTextSize(correctTextSize);
		
		mProgressBar = (ProgressBar) v.findViewById(R.id.time_tracker_progress_bar);
		
		//get in-time calculated from TimeTrackerFragmentA	
		//get it from TimeTracker Activity - was passed to it by fragment A
		TimeTrackerActivity t = (TimeTrackerActivity)this.getActivity();
		
		Double inTimeHolder  = t.getTime();
		
		if(inTimeHolder != 0){
			outTimeHolder -=inTimeHolder;
			
			// get percentage and set progress
			mProgressBar.setProgress(calcPercentageTime(inTimeHolder));
			
			//get user details
			fragmentUser.setText(t.getUserDetails());
			
			//display in & out of office times
			//In office hours and minutes
			String intime = Double.toString(inTimeHolder);		
			int inHours = Integer.parseInt(intime.substring(0, intime.indexOf(".")));		
			int inMinutes =  (int) (inTimeHolder - inHours)*60;
			String inminutes = String.format("%02d",inMinutes);
					
			//Out office hours and minutes
			String outtime = Double.toString(outTimeHolder);
			int outHours = Integer.parseInt(outtime.substring(0, outtime.indexOf(".")));		
			int outMinutes =  (int) (outTimeHolder - outHours)*60;
			String outminutes = String.format("%02d",outMinutes);
			
			fragmentInTime.setText(inHours + ":" + inminutes);
			fragmentOutTime.setText(outHours + ":" + outminutes);
		}
		else{
			fragmentUser.setText(null);
			fragmentInTime.setText("0:00");
			fragmentOutTime.setText("24:00");
		}
		
		home = (ImageButton) v.findViewById(R.id.home_button_time_tracker_b);
		Activity c = getActivity();
		if (c != null) {
			home.setOnClickListener(new SwitchButtonListener(c, "icreep.app.IcreepMenu"));
		}				
		return v;
	}
	
	/**
	 * Needs to update the map displayed - should be updated in TimeTracker Activity upon fragment selection
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	/**
	 * Calculates the percentage of time the user has spent in the office
	 * @param inOffice - time spent in the office
	 * @return percentage - the percentage rounded to nearest integer
	 */
	private int calcPercentageTime(double inOffice) { 
		return (int) (inOffice/24)*100;
	}

}
