package icreep.app.beacon;

import java.util.ArrayList;

import com.radiusnetworks.ibeacon.IBeacon;

import icreep.app.ICreepApplication;
import icreep.app.Message;
import icreep.app.R;
import icreep.app.SharedPreferencesControl;
import icreep.app.SwitchButtonListener;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class BeaconSelectionActivity extends Activity {
	
	private int NOT_SELECTED = -1;
	
	private TextView bossTrackingValue;
	private ListView beaconListView;
	private Switch switched;
	private Button save;
	private ImageButton home;
	private Button updateButton;
	
	private SharedPreferencesControl spc;
	private ICreepApplication mApplication;
	private BeaconListAdapter mAdapter;
	
	private int selectedIndex = NOT_SELECTED;
	private boolean checkedSwitch = false;
	private String currentBoss = "";
	private ArrayList<IBeacon> beaconList = new ArrayList<IBeacon>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_boss);
		spc = new SharedPreferencesControl(this);
		mApplication = (ICreepApplication) getApplicationContext();
		mAdapter = new BeaconListAdapter(this, beaconList);
		
		bossTrackingValue = (TextView) findViewById(R.id.bossTrackingValue);
		switched = (Switch) findViewById(R.id.switchBarBoss);
		save = (Button) findViewById(R.id.bossSaveButton);
		beaconListView = (ListView) findViewById(R.id.boss_listview);
		updateButton = (Button) findViewById(R.id.bossScanButton);
		home = (ImageButton) findViewById(R.id.home_button_boss);
		home.setOnClickListener(new SwitchButtonListener(this,
				"icreep.app.MainMenuActivity"));
		
		currentBoss = spc.getBossBeaconDetails();

		setTheChecker();
		switched.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// TODO Auto-generated method stub
				if (isChecked == true)
				{
					updateButton.setEnabled(true);
					updateButton.setBackground(getResources().getDrawable(R.drawable.reports_buttons_on));					
				}else
				{
					updateButton.setEnabled(false);
					updateButton.setBackground(getResources().getDrawable(R.drawable.reports_button_off));
				}
			}
		});
		
		beaconListView.setAdapter(mAdapter);
		beaconListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		beaconListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)	{
				// TODO Auto-generated method stub
				if (selectedIndex != position) {
					mAdapter.notifyDataSetChanged();
					beaconListView.setItemChecked(position, true);
					view.setBackgroundColor(Color.BLUE);
					selectedIndex = position;
					// mAdapter.notifyDataSetChanged();
				}
			}
		});
		
		save.setOnClickListener(new OnClickListener()	{
			
			@Override
			public void onClick(View v)	{
				if (validateSave()) {
					saveBossDetails();
				}
			}
		});
		
		updateButton.setOnClickListener(new OnClickListener()	{
			
			@Override
			public void onClick(View v)	{
				updateList();
			}
		});
	}
	
	
	public void updateList() {
		
		this.beaconList = (ArrayList<IBeacon>) mApplication.getBeaconList();
		
		mAdapter.clear();
		mAdapter.addAll(this.beaconList);
		mAdapter.notifyDataSetChanged();
		 
		selectedIndex = -1;
	}
	
	private boolean validateSave()
	{
		
		if (selectedIndex == NOT_SELECTED)
		{
			Message.message(this, "Please select an item in order to save");
			return false ;
		}
		
		IBeacon selectedBeacon = this.beaconList.get(selectedIndex);

		if (currentBoss.equals(selectedBeacon.getProximityUuid()) == true)
		{
			Message.message(this, "The user for the alert, hasn't changed");
			return false ;
		}	
		
		return true;
	}
	
	private void saveBossDetails() {
		IBeacon beacon = this.beaconList.get(selectedIndex);
		currentBoss = beacon.getProximityUuid();
		bossTrackingValue.setText(currentBoss);
		spc.writeBossBeaconDetails(currentBoss);
	}
	
	private void setTheChecker()
	{
		//boolean checked = false ;
		currentBoss = spc.getBossBeaconDetails();
		if (currentBoss.equals(""))
		{
			checkedSwitch = false; 
			switched.setChecked(checkedSwitch);		
			return;
		}
		
		checkedSwitch = true;
		switched.setChecked(checkedSwitch);
		updateButton.setEnabled(true);
		updateButton.setBackground(getResources().getDrawable(R.drawable.reports_buttons_on));
		bossTrackingValue.setText(currentBoss);		
	}

}
