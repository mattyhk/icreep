package icreep.app.location;

import icreep.app.ICreepApplication;
import icreep.app.Message;
import icreep.app.R;
import icreep.app.SwitchButtonListener;
import icreep.app.db.iCreepDatabaseAdapter;
import icreep.app.report.TimePlace;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class LocationFragmentB extends Fragment implements OnItemClickListener {
	
	private int INTERVAL = 5000;
	private String FILE_FRAGMENT = "zones_3_";
	private int OUTDOORS = -1;
	
	private ListView listView = null;
	private TextView userName;
	private ImageButton home;
	
	private ArrayList<TimePlace> zones = new ArrayList<TimePlace>(); 
	private DailyMovementAdapter mAdapter;
	private iCreepDatabaseAdapter iCreepHelper;
	private int userID;
	private Handler mHandler;
	private UserLocation user;
	private Toast t;
	private ICreepApplication mApplication;

	public LocationFragmentB() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_location_b, container, false);
		
		user = new UserLocation(getActivity());
		
		mApplication = (ICreepApplication) getActivity().getApplicationContext();
		
		userID = user.getUserID();
		iCreepHelper = new iCreepDatabaseAdapter(getActivity());
		
		t = new Toast(getActivity());
		
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
		t.cancel();
		t = new Toast(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View layout = inflater.inflate(R.layout.location_toast,
		                               (ViewGroup)getActivity().findViewById(R.id.toast_layout_root));
		TextView tv = (TextView) layout.findViewById(R.id.toast_title);
		
		Integer drawID;
		
		if (item.getZoneID() == OUTDOORS) {
			drawID = getActivity().getResources()
					.getIdentifier("zones_outdoors", "drawable", getActivity().getPackageName());
		}
		
		else {
			drawID =  getActivity().getResources()
					.getIdentifier(FILE_FRAGMENT + item.getZoneID(), "drawable", getActivity()
							.getPackageName());
		}
						
		ImageView zoneMap = (ImageView) layout.findViewById(R.id.toast_map);
		
		if (drawID != null) {
			zoneMap.setImageResource(drawID);
		}
		
		
		tv.setText(item.getFloor() + " : " + item.getLocation());
		t.setGravity(Gravity.CENTER, 0, 0);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setView(layout);
		t.show();
		
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
		List<TimePlace> z = user.getVisitedZones();
		Log.d("TEST", "List received is size " + z.size());
		if (z.size() > 0){
			for (TimePlace tp : z){
				mAdapter.add(tp);
			}
		}
		else if (mApplication.getCurrentLocation() == -2 && z.size() == 0) {
			Message.message(getActivity(), "You have yet to visit a zone");
		}
		
		mAdapter.notifyDataSetChanged();
	}

}
