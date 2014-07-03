package icreep.app.TimeTracker;

import icreep.app.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TimeTrackerFragmentB extends Fragment {
	
	ProgressBar mProgressBar;

	public TimeTrackerFragmentB() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_time_tracker_b, container, false);
		
		TextView fragmentTitle = (TextView) v.findViewById(R.id.time_tracker_a_title);
		TextView fragmentUser = (TextView) v.findViewById(R.id.time_tracker_a_user);
		
		float correctTextSize = 16*getResources().getDisplayMetrics().density;
		fragmentTitle.setTextSize(correctTextSize);
		fragmentUser.setTextSize(correctTextSize);
		
		mProgressBar = (ProgressBar) v.findViewById(R.id.time_tracker_progress_bar);
		
		// Place holder for time being
		mProgressBar.setProgress(65);
		
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
	private int calcPercentageTime(float inOffice, float outOffice) {
		return 0;
	}

}
