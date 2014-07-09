package icreep.app.location;


import icreep.app.R;
import icreep.app.SwitchButtonListener;
import icreep.app.db.iCreepDatabaseAdapter;
import icreep.app.report.TimePlace;

import java.util.ArrayList;
import java.util.Set;

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


public class LocationFragmentB extends Fragment implements OnItemClickListener {
	
	private int INTERVAL = 10000;
	
	private ListView listView = null;
	private TextView userName;
	private ImageButton home;
	
	private ArrayList<TimePlace> zones = new ArrayList<TimePlace>(); 
	private DailyMovementAdapter mAdapter;
	private iCreepDatabaseAdapter iCreepHelper;
	private int userID;
	private Handler mHandler;
	private UserLocation user;
	

	public LocationFragmentB() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_location_b, container, false);
		
		user = new UserLocation(getActivity());
		
		userID = user.getUserID();
		iCreepHelper = new iCreepDatabaseAdapter(getActivity());
		
		home = (ImageButton) v.findViewById(R.id.home_button_location_movement);
		Activity c = getActivity();
		if (c != null) {
			home.setOnClickListener(new SwitchButtonListener(c, "icreep.app.IcreepMenu"));
		}
		
		userName = (TextView) v.findViewById(R.id.location_b_user);
		userName.setText(iCreepHelper.getUserDetails(userID));
		
		listView = (ListView) v.findViewById(R.id.daily_movement_listView_main);
		
        mAdapter = new DailyMovementAdapter(getActivity(), zones);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        
        mHandler = new Handler();
        
        updateList();

		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		TimePlace item = (TimePlace) zones.get(position);
		Toast.makeText(getActivity(), "You clicked " + item.getZoneID() , Toast.LENGTH_SHORT).show();
		
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
		Set<TimePlace> z = user.getVisitedZones();
		for (TimePlace tp : z){
			mAdapter.add(tp);
		}
		mAdapter.notifyDataSetChanged();
	}

}
