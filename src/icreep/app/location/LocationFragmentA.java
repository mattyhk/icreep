package icreep.app.location;

import icreep.app.ICreepApplication;
import icreep.app.R;
import icreep.app.SwitchButtonListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationFragmentA extends Fragment {
	
	private int INTERVAL = 5000;
	private String FILE_FRAGMENT = "zones_3_";
	
	private Handler mHandler;
	
	private ICreepApplication mApplication;
	
	private TextView floorTextView;
	private ImageButton home;
	private ImageView zoneMap;
	
	
	public LocationFragmentA() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		View v = inflater.inflate(R.layout.fragment_location_a, container, false);
		
		home = (ImageButton) v.findViewById(R.id.home_button_location_current);
		Activity c = getActivity();
		if (c != null) {
			home.setOnClickListener(new SwitchButtonListener(c, "icreep.app.IcreepMenu"));
		}
		
		mHandler = new Handler();
		mApplication = (ICreepApplication) getActivity().getApplicationContext();
		floorTextView = (TextView) v.findViewById(R.id.location_a_floor_text_view);
		zoneMap = (ImageView) v.findViewById(R.id.location_a_zone_map);
		
		return v;
	}
	
	/**
	 * Sets the map to be displayed as determined by the current zone
	 */
	private void updateImage(){
		int currentLocation = mApplication.getCurrentLocation();
		Integer drawID = null;
		
		if (currentLocation == -2) {
			// Location is unknown
			drawID = getActivity().getResources()
						.getIdentifier("zones_all", "drawable", getActivity().getPackageName());
		}
		
		else if (currentLocation == -1) {
			// Location is out of office
			drawID = getActivity().getResources()
						.getIdentifier("zones_outdoor", "drawable", getActivity().getPackageName());
		}
		
		else {
			drawID = getActivity().getResources()
						.getIdentifier(FILE_FRAGMENT + currentLocation, "drawable", getActivity()
							.getPackageName());
		}
		
		if (drawID != null) {
			zoneMap.setImageResource(drawID);
		}
	}
	
	/**
	 * Update the current floor displayed
	 */
	private void updateFloor() {
		floorTextView.setText("Floor " + mApplication.getCurrentFloor());
	}
	
	/**
	 * Needs to update the map displayed - should be updated in Location Activity upon fragment selection
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// May need to move this to activity - can call it to run only when tab is selected
		startRepeatingTask();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		// Again may need to move this to activity
		stopRepeatingTask();
	}
	
	private Runnable mLocChecker = new Runnable() {
		
		@Override
		public void run() {
			updateImage();
			updateFloor();
			mHandler.postDelayed(mLocChecker, INTERVAL);
		}
	};
	
	private void startRepeatingTask() {
		mLocChecker.run();
	}
	
	private void stopRepeatingTask() {
		mLocChecker.run();
	}

}
