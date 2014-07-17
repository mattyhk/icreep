/**
 *  THIS CLASS IS NOT BEING USED
 */


package icreep.app.bluetooth;

import icreep.app.R;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BluetoothDevicesAdapter extends ArrayAdapter<BlueToothDeviceModel>
{
	private ArrayList<BlueToothDeviceModel> devices;
	private Context c;
	private LayoutInflater vi;

	public BluetoothDevicesAdapter(Context context,
			ArrayList<BlueToothDeviceModel> devices)
	{
		super(context, 0, devices);
		this.devices = devices;
		c = context;
		vi = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		View v = convertView;
		final BlueToothDeviceModel btm = devices.get(position);

		if (btm != null) {
			v = vi.inflate(R.layout.list_boss_device, null);
			v.setBackgroundColor(c.getResources().getColor(R.color.whiteBackground));
			final TextView name = (TextView) v
					.findViewById(R.id.view_of_boss_name);
			final TextView mac = (TextView) v
					.findViewById(R.id.view_of_boss_mac);

			if (name != null) {
				name.setText("Name: " + btm.getDeviceName());
			}

			if (mac != null) {
				mac.setText("Mac: " + btm.getDeviceUniqueID());
			}
		}
		return v;
	}
	
	
}
