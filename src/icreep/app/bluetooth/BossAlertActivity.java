/**
 *  THIS CLASS IS NOT BEING USED
 */


package icreep.app.bluetooth;

import java.util.ArrayList;

import icreep.app.bluetooth.DeviceListGenerator;

import icreep.app.MainMenuActivity;
import icreep.app.Message;
import icreep.app.R;
import icreep.app.SharedPreferencesControl;
import icreep.app.SwitchButtonListener;
import android.app.Activity;
import android.content.Intent;
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

public class BossAlertActivity extends Activity
{
	private TextView bossTrackingValue;
	private ListView listingoffoundvalues;
	private Switch switched;
	private SharedPreferencesControl spc;
	private Button save;
	private ImageButton home;
	private BluetoothDevicesAdapter mAdapter;
	private int selectedIndex = -1;
	private ArrayList<BlueToothDeviceModel> devices = new ArrayList<BlueToothDeviceModel>();
	private boolean checkedSwitch = false;
	private ArrayList<String> pingDet = new ArrayList<String>();
	private Button scanner ;
	private DeviceListGenerator bdm ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boss);
		spc = new SharedPreferencesControl(this);
		bossTrackingValue = (TextView) findViewById(R.id.bossTrackingValue);
		switched = (Switch) findViewById(R.id.switchBarBoss);
		save = (Button) findViewById(R.id.bossSaveButton);
		listingoffoundvalues = (ListView) findViewById(R.id.boss_listview);
		scanner = (Button) findViewById(R.id.bossScanButton);
		home = (ImageButton) findViewById(R.id.home_button_boss);
		home.setOnClickListener(new SwitchButtonListener(this,
				"icreep.app.MainMenuActivity"));
		bdm = new DeviceListGenerator(this);
		mAdapter = new BluetoothDevicesAdapter(this, devices);

		
		
		setTheChecker();
		switched.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// TODO Auto-generated method stub
				if (isChecked == true)
				{
					scanner.setEnabled(true);
					scanner.setBackground(getResources().getDrawable(R.drawable.reports_buttons_on));					
				}else
				{
					scanner.setEnabled(false);
					scanner.setBackground(getResources().getDrawable(R.drawable.reports_button_off));
				}
			}
		});
		
		listingoffoundvalues.setAdapter(mAdapter);
		listingoffoundvalues.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listingoffoundvalues.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				// TODO Auto-generated method stub
				if (selectedIndex != position) {
					mAdapter.notifyDataSetChanged();
					listingoffoundvalues.setItemChecked(position, true);
					view.setBackgroundColor(Color.BLUE);
					selectedIndex = position ;
					// mAdapter.notifyDataSetChanged();
				}
			}
		});
		
		save.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				boolean canSave = validateSave();
				if (canSave == true)
				{
					if ((pingDet.size()>0) && (switched.isChecked() == false))
					{
						pingDet.clear();
						pingDet.add("");
						pingDet.add("");
						spc.removeBossDetails();
						selectedIndex = -1;
						bossTrackingValue.setText("");
					}else 
					{
						BlueToothDeviceModel cur = devices.get(selectedIndex);
						spc.writeBossDetails(cur.getDeviceName(), cur.getDeviceUniqueID());
						pingDet.clear();
						pingDet.add(cur.getDeviceName());
						pingDet.add(cur.getDeviceUniqueID());
						bossTrackingValue.setText(cur.getDeviceName());						
						selectedIndex = -1;						
						return ;
					}
					
				}
			}
		});
		
		scanner.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	public void startScanning()
    {
    	//device list generator
    	bdm.startDiscovering();    	
    }
	
	public void updateList(ArrayList<BlueToothDeviceModel> newDevices)
	{
		devices = newDevices;
		// changed my values
			 mAdapter.clear();
			 mAdapter.addAll(devices);
			 mAdapter.notifyDataSetChanged();
			 selectedIndex = -1;
	}
	
	public boolean validateSave()
	{
		
		if (pingDet.size() > 0)
		{
			if (switched.isChecked() == false)
			{
				return true ;
			}
		}
		
		if (selectedIndex == -1)
		{
			Message.message(this, "Please select an item in order to save");
			return false ;
		}
		
		BlueToothDeviceModel cur = devices.get(selectedIndex);
		if (pingDet.get(1).equals(cur.getDeviceUniqueID()) == true)
		{
			Message.message(this, "The user for the alert, hasn't changed");
			return false ;
		}
		
		if (pingDet.size() == 0)
		{	
			return true;
		}	
		
		return true;
	}
	
	public void setTheChecker()
	{
		//boolean checked = false ;
		ArrayList<String> list = spc.getPingUserDetails();
		if (list.size() == 0)
		{
			checkedSwitch = false; 
			switched.setChecked(checkedSwitch);		
			return ;
		}
		
		checkedSwitch = true ;
		switched.setChecked(checkedSwitch);	
		pingDet = list ;
		scanner.setEnabled(true);
		scanner.setBackground(getResources().getDrawable(R.drawable.reports_buttons_on));
		bossTrackingValue.setText(list.get(0));		
		
	}
	
	@Override
	public void onBackPressed() 
	{
	    Intent myIntent = new Intent(this, MainMenuActivity.class);
	    startActivity(myIntent);
	    super.onBackPressed();
	}
}
