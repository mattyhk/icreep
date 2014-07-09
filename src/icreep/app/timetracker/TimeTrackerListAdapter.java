package icreep.app.timetracker;

import icreep.app.R;
import icreep.app.location.FloorItem;
import icreep.app.location.ListItem;
import icreep.app.location.ZoneItem;
import icreep.app.report.TimePlace;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TimeTrackerListAdapter extends ArrayAdapter<TimePlace> {
	
	private Context context;
	private ArrayList<TimePlace> zones;
	private LayoutInflater vi;
	
	public TimeTrackerListAdapter(Context context, ArrayList<TimePlace> zones) {
		
		super(context, 0, zones);
		
		this.context = context;
		this.zones = zones;
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		
		final TimePlace timePlace = zones.get(position);
		
		if (timePlace != null) {

				v = vi.inflate(R.layout.list_item_zone_time, null);
				final TextView title = (TextView) v.findViewById(R.id.time_tracker_list_item_zone_title);
				final TextView subtitle = (TextView) v.findViewById(R.id.time_tracker_list_item_zone_summary);
				final TextView time = (TextView) v.findViewById(R.id.time_tracker_list_item_zone_time);
				
				if (title != null)
					title.setText(timePlace.getZoneID());
				if (subtitle != null)
					subtitle.setText(timePlace.getLocation());
				if (time != null)
					subtitle.setText(String.valueOf(timePlace.getTimeSpent()));
		}
		
		return v;
	}

}
