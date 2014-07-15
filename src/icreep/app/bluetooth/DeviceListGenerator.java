package icreep.app.bluetooth;

import java.util.ArrayList;
import java.util.Set;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

/**
 * This Activity appears as a dialog. It lists any paired devices and devices
 * detected in the area after discovery. When a device is chosen by the user,
 * the MAC address of the device is sent back to the parent Activity in the
 * result Intent.
 * 
 * the link is
 * http://stackoverflow.com/questions/3285580/how-to-periodically-scan
 * -for-bluetooth-devices-on-android
 */
public class DeviceListGenerator
{
	// Debugging
	private static final String TAG = "DeviceListActivity";
	private static final boolean D = true;

	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";

	// Member fields
	private BluetoothAdapter mBtAdapter;
	// private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	// private ArrayAdapter<String> mNewDevicesArrayAdapter;
	private ArrayList<BlueToothDeviceModel> devices;

	private Context c;

	/*
	 * Pre-Conditions: create instance
	 * 
	 * Post-Conditions: Creates bluetooth adapter for scanning Creates
	 * connection for listeners adds all unpaired devices
	 */
	public DeviceListGenerator(Context c)
	{

		// TODO Auto-generated constructor stub\

		this.c = c;
		devices = new ArrayList<BlueToothDeviceModel>(); // create empty list
															// for devices
		// Register for broadcasts when a device is discovered
		// This will add all the unpaired devices
		// added listener for all new devices
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		c.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		// and listener for when complete
		BossAlertActivity baa = (BossAlertActivity) c;
		
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		c.registerReceiver(mReceiver, filter);
		DeviceGeneratorListenerUpdate testt = new DeviceGeneratorListenerUpdate(this,(BossAlertActivity)c);
		c.registerReceiver(testt, filter);

		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		// Get a set of currently paired devices
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
		
		
		// If there are paired devices, add each one to the ArrayAdapter // adds
		// all the kmown paired devices
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				BlueToothDeviceModel newi = new BlueToothDeviceModel(device);
				devices.add(newi);
			}
		}
	}

	public void unRegisterReceiver()
	{
		c.unregisterReceiver(mReceiver);
	}

	/**
	 * Start device discover with the BluetoothAdapter
	 */
	public void startDiscovering()
	{
		if (D)
			Log.d(TAG, "doDiscovery()");

		// If we're already discovering, stop it
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}
		// Request discover from BluetoothAdapter
		Toast t=  Toast.makeText(c, "Scanning started", Toast.LENGTH_SHORT);
		t.show();
		mBtAdapter.startDiscovery();
		devices.clear();
	}

	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished
	private final BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();

			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// If it's already paired, skip it, because it's been listed
				// already
				// this will add all unpaired devices
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					BlueToothDeviceModel newi = new BlueToothDeviceModel(device);
					devices.add(newi);
				}
				// When discovery is finished, change the Activity title
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				// want to return the list of devices after done the scanning
				Toast t=  Toast.makeText(c, "Scanning finished", Toast.LENGTH_SHORT);
				t.show();
				
			}
		}
	};

	public ArrayList<BlueToothDeviceModel> afterDoneScanning()
	{
		return devices;
	}
}