package icreep.app.timetracker;

import icreep.app.R;
import icreep.app.SwitchButtonListener;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TimeTrackerFragmentB extends Fragment {
	
	private ProgressBar mProgressBar;
	private ImageButton home;
	
	public double inTimeHolder = 0;

	public TimeTrackerFragmentB() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_time_tracker_b, container, false);
		
		TextView fragmentTitle = (TextView) v.findViewById(R.id.time_tracker_b_title);
		TextView fragmentUser = (TextView) v.findViewById(R.id.time_tracker_b_user);
		
		//float correctTextSize = 16*getResources().getDisplayMetrics().density;
		//fragmentTitle.setTextSize(correctTextSize);
		//fragmentUser.setTextSize(correctTextSize);
		
		mProgressBar = (ProgressBar) v.findViewById(R.id.time_tracker_progress_bar);
		
		//get in-time calculated from TimeTrackerFragmentA	
		//get this from TimeTracker Activity - was passed to it by fragment A
		TimeTrackerActivity t = (TimeTrackerActivity)this.getActivity();
		Double inTimeHolder  = t.getTime();
		
		// get percentage and set progress
		mProgressBar.setProgress(calcPercentageTime(inTimeHolder));
		
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
	 * @param outOffice - time spent out of the office
	 * @return percentage - the percentage rounded to nearest integer
	 */
	private int calcPercentageTime(double inOffice) {
		
		return (int) (inOffice/24)*100;
	}

}
