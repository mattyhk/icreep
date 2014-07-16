package icreep.app.beacon;

import icreep.app.R;

import java.util.ArrayList;

import com.radiusnetworks.ibeacon.IBeacon;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BeaconListAdapter extends ArrayAdapter<IBeacon> {
	
	private ArrayList<IBeacon> beacons;
	private LayoutInflater vi;
	private Context context;
	BeaconSelectionActivity bsa ;

	public BeaconListAdapter(Context context, ArrayList<IBeacon> objects) {
		super(context, 0, objects);
		
		this.beacons = objects;
		this.vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.bsa = (BeaconSelectionActivity) context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		final IBeacon beacon = this.beacons.get(position);
		
		if (beacon != null) {
			v = vi.inflate(R.layout.list_boss_device, null);
			v.setBackgroundColor(context.getResources().getColor(R.color.whiteBackground));
			if (bsa.selectedIndex==position)
			{
				v.setBackgroundColor(Color.CYAN);
			}else v.setBackgroundColor(context.getResources().getColor(R.color.whiteBackground));
			
			final TextView name = (TextView) v
					.findViewById(R.id.view_of_boss_name);
			
			if (name != null) {
				name.setText("Minor: " + beacon.getMinor());
			}
		}
		
		return v;
	}
	
	

}
