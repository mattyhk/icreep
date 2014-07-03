package icreep.app.TimeTracker;

import icreep.app.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TimeTrackerFragmentB extends Fragment {

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
		
		return v;
	}
	
	/**
	 * Returns the current floor of the user
	 * @return floor
	 */
	private int getFloor(){
		return 0;
	}
	
	/**
	 * Sets the map to be displayed as determined by the current zone and floor
	 * @param floor
	 * @param currentZone
	 */
	private void setImage(int floor, int currentZone){
		
	}
	
	/**
	 * Needs to update the map displayed - should be updated in TimeTracker Activity upon fragment selection
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
